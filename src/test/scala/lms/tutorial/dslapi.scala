package scala.lms.tutorial

import scala.virtualization.lms.common._
import scala.reflect.SourceContext

trait Dsl extends NumericOps with BooleanOps with LiftNumeric with LiftBoolean with IfThenElse with Equal with RangeOps with OrderingOps with MiscOps with ArrayOps with StaticData {
  def comment[A:Manifest](l: String)(b: => Rep[A])
}
trait DslExp extends Dsl with NumericOpsExpOpt with BooleanOpsExp with CompileScala with IfThenElseExpOpt with EqualExpBridgeOpt with RangeOpsExp with OrderingOpsExp with MiscOpsExp with EffectExp with ArrayOpsExpOpt with StaticDataExp {


  case class Comment[A:Manifest](l: String, b: Block[A]) extends Def[A]
  def comment[A:Manifest](l: String)(b: => Rep[A]) = {
    val br = reifyEffects(b)
    val be = summarizeEffects(br)
    reflectEffect(Comment(l, br), be)
  }
  override def boundSyms(e: Any): List[Sym[Any]] = e match {
    case Comment(s, b) => effectSyms(b)
    case _ => super.boundSyms(e)
  }

  override def array_apply[T:Manifest](x: Exp[Array[T]], n: Exp[Int])(implicit pos: SourceContext): Exp[T] = (x,n) match {
    case (Def(StaticData(x:Array[T])), Const(n)) => Const(x(n))
    case _ => super.array_apply(x,n)
  }
}
trait DslGen extends ScalaGenNumericOps with ScalaGenBooleanOps with ScalaGenIfThenElse with ScalaGenEqual with ScalaGenRangeOps with ScalaGenOrderingOps with ScalaGenMiscOps with ScalaGenArrayOps with ScalaGenStaticData {
  val IR: DslExp

  import IR._
  
  override def emitNode(sym: Sym[Any], rhs: Def[Any]) = rhs match {
    case Comment(s,b) =>
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

abstract class DslSnippet[A:Manifest,B:Manifest] extends Dsl {
  def snippet(x: Rep[A]): Rep[B]
}

abstract class DslDriver[A:Manifest,B:Manifest] extends DslSnippet[A,B] with DslImpl {
  lazy val f = compile(snippet)
  def eval(x: A): B = f(x)
  lazy val code: String = {
    val source = new java.io.StringWriter()
    codegen.emitSource(snippet, "Snippet", new java.io.PrintWriter(source))
    source.toString
  }
}
class DslApiTest extends TutorialFunSuite {
  val under = "dslapi"
  test("1") {
    val snippet = new DslDriver[Int,Int] {
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
    val snippet = new  DslDriver[Int,Int] {
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
    val snippet = new DslDriver[Int,Int] {
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
    val snippet = new DslDriver[Int,Unit] {
      def snippet(x: Rep[Int]) = {
        comment("for") {
          //#range1
          for (i <- (0 until 3): Range) {
            println(i)
          }
          //#range1
        }
      }
    }
    check("range1", snippet.code)
  }

  test("range2") {
    val snippet = new DslDriver[Int,Unit] {
      def snippet(x: Rep[Int]) = {
        comment("for") {
          //#range2
          for (i <- (0 until x): Rep[Range]) {
            println(i)
          }
          //#range2
        }
      }
    }
    check("range2", snippet.code)
  }

  test("shonan-hmm") {
    val snippet = new DslDriver[Array[Int],Array[Int]] {
      //#unrollIf
      def unrollIf(range: Range)(cond: Boolean) = new {
        def foreach(f: Rep[Int] => Rep[Unit]): Rep[Unit] = {
          if (cond) for (i <- range) f(i)
          else for (i <- (range.start until range.end): Rep[Range]) f(i)
        }
      }
      //#unrollIf
      val A = scala.Array

      val a =
        //#ex-a
        A(A(1, 1, 1, 1, 1), // dense
          A(0, 0, 0, 0, 0), // null
          A(0, 0, 1, 0, 0), // sparse
          A(0, 0, 0, 0, 0),
          A(0, 0, 1, 0, 1))
        //#ex-a

      def infix_at(a: Array[Array[Int]], i: Int, j: Rep[Int]): Rep[Int] = {
        (staticData(a(i)) apply j)
      }

      def sparse(ai: Array[Int]): Boolean = {
        ai.filter(_ != 0).length < 3
      }

      //#matrix_vector_prod
      def matrix_vector_prod(a: Array[Array[Int]], v: Rep[Array[Int]]) = {
        val n = a.length
        val v1 = NewArray[Int](n)
        for (i <- 0 until n: Range) {
          comment("index_" + i) {
            for (j <- unrollIf(0 until n)(sparse(a(i)))) {
              v1(i) = v1(i) + a.at(i, j) * v(j)
            }
          }
        }
        v1
      }
      //#matrix_vector_prod

      def snippet(v: Rep[Array[Int]]) = {
        matrix_vector_prod(a, v)
      }
    }
    check("shonan-hmm", snippet.code)
  }
}
