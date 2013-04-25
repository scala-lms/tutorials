package scala.lms.tutorial

import scala.virtualization.lms.common._

trait Dsl extends NumericOps with BooleanOps with LiftNumeric with LiftBoolean with IfThenElse with Equal with LiftEquals with RangeOps with OrderingOps with MiscOps {
  def label[A:Manifest](l: String)(b: => Rep[A])
}
trait DslExp extends Dsl with NumericOpsExpOpt with BooleanOpsExp with CompileScala with IfThenElseExpOpt with EqualExpBridgeOpt with RangeOpsExp with OrderingOpsExp with MiscOpsExp with EffectExp {
  case class Label[A:Manifest](l: String, b: Block[A]) extends Def[A]
  def label[A:Manifest](l: String)(b: => Rep[A]) = {
    val br = reifyEffects(b)
    val be = summarizeEffects(br)
    reflectEffect(Label(l, br), be)
  }
  override def boundSyms(e: Any): List[Sym[Any]] = e match {
    case Label(s, b) => effectSyms(b)
    case _ => super.boundSyms(e)
  }
}
trait DslGen extends ScalaGenNumericOps with ScalaGenBooleanOps with ScalaGenIfThenElse with ScalaGenEqual with ScalaGenRangeOps with ScalaGenOrderingOps with ScalaGenMiscOps {
  val IR: DslExp

  import IR._
  
  override def emitNode(sym: Sym[Any], rhs: Def[Any]) = rhs match {
    case Label(s,b) =>
      stream.println("val " + quote(sym) + " = {")
      stream.println("//#" + s)
      stream.println("// generated code")
      emitBlock(b)
      stream.println(quote(getBlockResult(b)))
      stream.println("//#" + s)
      stream.println("}")
    case _ => super.emitNode(sym, rhs)
  }
}
trait DslImpl extends DslExp { q =>
  object codegen extends DslGen {
    val IR: q.type = q
  }
}

trait DslSnippet extends Dsl {
  def snippet(x: Rep[Int]): Rep[Int]
}

trait DslDriver extends DslSnippet with DslImpl {
  lazy val f = compile(snippet)
  def eval(x: Int): Int = f(x)
  lazy val code: String = {
    val source = new java.io.StringWriter()
    codegen.emitSource(snippet, "Snippet", new java.io.PrintWriter(source))
    source.toString
  }
}
class DslApiTest extends TutorialFunSuite {
  val under = "dslapi"
  test("1") {
    val snippet = new DslSnippet with DslDriver {
      //#1
      def snippet(x: Rep[Int]) = {
        def compute(b: Boolean): Rep[Int] = {
          // the if is executed in the first stage
          if (b) 1 else x
        }
        compute(true)+compute(1==1)
      }
      //#1
    }
    assert(snippet.eval(0) === 2)
    check("1", snippet.code)
  }

  test("2") {
    val snippet = new DslSnippet with DslDriver {
      //#2
      def snippet(x: Rep[Int]) = {
        def compute(b: Rep[Boolean]): Rep[Int] = {
          // the if is deferred to the second stage
          if (b) 1 else x
        }
        compute(x==1)
      }
      //#2
    }
    assert(snippet.eval(2) === 2)
    check("2", snippet.code)
  }

  test("power") {
    val snippet = new DslSnippet with DslDriver {
      //#power
      def power(b: Rep[Int], x: Int): Rep[Int] = {
        if (x == 0) 1
        else b * power(b, x-1)
      }
      def snippet(b: Rep[Int]) =
        power(b, 3)
      //#power
    }
    assert(snippet.eval(2) === 8)
    check("power", snippet.code)
  }

  test("range1") {
    val snippet = new DslSnippet with DslDriver {
      def snippet(x: Rep[Int]) = {
        label("for") {
          //#range1
          for (i <- (0 until 3): Range) {
            println(i)
          }
          //#range1
        }
        0
      }
    }
    check("range1", snippet.code)
  }

  test("range2") {
    val snippet = new DslSnippet with DslDriver {
      def snippet(x: Rep[Int]) = {
        label("for") {
          //#range2
          for (i <- (0 until x): Rep[Range]) {
            println(i)
          }
          //#range2
        }
        0
      }
    }
    check("range2", snippet.code)
  }
}
