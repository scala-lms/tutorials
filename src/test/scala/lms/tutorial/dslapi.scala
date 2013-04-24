package scala.lms.tutorial

import scala.virtualization.lms.common._

trait Dsl extends NumericOps with BooleanOps with LiftNumeric with LiftBoolean with IfThenElse with Equal with LiftEquals
trait DslExp extends Dsl with NumericOpsExpOpt with BooleanOpsExp with CompileScala with IfThenElseExpOpt with EqualExpBridgeOpt
trait DslGen extends ScalaGenNumericOps with ScalaGenBooleanOps with ScalaGenIfThenElse with ScalaGenEqual {
  val IR: DslExp
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

}
