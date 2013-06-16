package scala.lms.tutorial

import scala.virtualization.lms.common._
import scala.reflect.SourceContext

trait Dsl extends NumericOps with BooleanOps with LiftString with LiftNumeric with LiftBoolean with IfThenElse with Equal with RangeOps with OrderingOps with MiscOps with ArrayOps with StringOps with SeqOps with Functions with While with StaticData with Variables with LiftVariables {
  implicit def repStrToSeqOps(a: Rep[String]) = new SeqOpsCls(a.asInstanceOf[Rep[Seq[Char]]])
  def infix_&&&(lhs: Rep[Boolean], rhs: => Rep[Boolean]): Rep[Boolean] =
    __ifThenElse(lhs, rhs, unit(false))
  def comment[A:Manifest](l: String, verbose: Boolean = true)(b: => Rep[A])
}
trait DslExp extends Dsl with NumericOpsExpOpt with BooleanOpsExp with CompileScala with IfThenElseExpOpt with EqualExpBridgeOpt with RangeOpsExp with OrderingOpsExp with MiscOpsExp with EffectExp with ArrayOpsExpOpt with StringOpsExp with SeqOpsExp with FunctionsRecursiveExp with WhileExp with StaticDataExp with VariablesExp {
  override def boolean_or(lhs: Exp[Boolean], rhs: Exp[Boolean])(implicit pos: SourceContext) : Exp[Boolean] = lhs match {
    case Const(false) => rhs
    case _ => super.boolean_or(lhs, rhs)
  }

  case class Comment[A:Manifest](l: String, verbose: Boolean, b: Block[A]) extends Def[A]
  def comment[A:Manifest](l: String, verbose: Boolean)(b: => Rep[A]) = {
    val br = reifyEffects(b)
    val be = summarizeEffects(br)
    reflectEffect(Comment(l, verbose, br), be)
  }
  override def boundSyms(e: Any): List[Sym[Any]] = e match {
    case Comment(_, _, b) => effectSyms(b)
    case _ => super.boundSyms(e)
  }

  override def array_apply[T:Manifest](x: Exp[Array[T]], n: Exp[Int])(implicit pos: SourceContext): Exp[T] = (x,n) match {
    case (Def(StaticData(x:Array[T])), Const(n)) => 
      val y = x(n)
      if (y.isInstanceOf[Int]) unit(y) else staticData(y)
    case _ => super.array_apply(x,n)
  }
}
trait DslGen extends ScalaGenNumericOps with ScalaGenBooleanOps with ScalaGenIfThenElse with ScalaGenEqual with ScalaGenRangeOps with ScalaGenOrderingOps with ScalaGenMiscOps with ScalaGenArrayOps with ScalaGenStringOps with ScalaGenSeqOps with ScalaGenFunctions with ScalaGenWhile with ScalaGenStaticData with ScalaGenVariables {
  val IR: DslExp

  import IR._
  
  override def emitNode(sym: Sym[Any], rhs: Def[Any]) = rhs match {
    case IfThenElse(c,Block(Const(true)),Block(Const(false))) =>
      emitValDef(sym, quote(c))
    case Comment(s, verbose, b) =>
      stream.println("val " + quote(sym) + " = {")
      stream.println("//#" + s)
      if (verbose) {
        stream.println("// generated code for " + s.replace('_', ' '))
      } else {
        stream.println("// generated code")
      }
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
        comment("for", verbose = false) {
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
        comment("for", verbose = false) {
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


/* STEP 0: starting point

  this is our static implementation for the shonan challenge problem
*/
  test("shonan-hmm1a") {

    val A = scala.Array

    val a =
      A(A(1, 1, 1, 1, 1), // dense
        A(0, 0, 0, 0, 0), // null
        A(0, 0, 1, 0, 0), // sparse
        A(0, 0, 0, 0, 0),
        A(0, 0, 1, 0, 1))

    val v = A(1,1,1,1,1)


    def matrix_vector_prod(a: Array[Array[Int]], v: Array[Int]) = {
      val n = a.length
      val v1 = new Array[Int](n)

      for (i <- (0 until n)) {
          for (j <- (0 until n)) {
            v1(i) = v1(i) + a(i)(j) * v(j)
          }
      }
      v1
    }

    val v1 = matrix_vector_prod(a, v)

    check("shonan-hmm1a", v1.mkString(","))
  }

/* STEP 0.5: static vs dynamic conditional

  now we're adding some basic codgen facilities and play
  with static vs dynamic expressions. fully static expressions
  do not show up in generated code. if we change the 
  condition to depend on the (dynamic) input array, an if/else
  statement will be generated.
*/

  test("shonan-hmm1b") {
    val snippet = new DslDriver[Array[Int],Array[Int]] {
      def snippet(v: Rep[Array[Int]]) = {

        val x = 100 - 5 //* v0.length

        if (x > 10) {
          println("yep")
        }

        v
 
      }
    }
    check("shonan-hmm1b", snippet.code)
  }


/* STEP 1: staging the matrix vector prod

  we want to change the mv prod function to take a dynamic vector
  and a static matrix. we move it into the DslDriver trait so
  it can use Rep.

  we generate nested loops by default (Rep[Range]). to make that
  work, we need to lift the static matrix to a Rep value. we
  do that using staticData.

  now we can play with the loops shapes: Range loops are computed
  statically, i.e. unrolled at staging time. Rep[Range] loops cause
  generation of loop code. 

  we can make unrolling decisions in a very fine-grained manner:
  for dense rows we want to generate a loop, for sparse rows
  we want to unroll. we just add a condition `sparse` and 
  either do a Range or Rep[Range] loop.
*/

  test("shonan-hmm1c") {

    val A = scala.Array

    val a =
      A(A(1, 1, 1, 1, 1), // dense
        A(0, 0, 0, 0, 0), // null
        A(0, 0, 1, 0, 0), // sparse
        A(0, 0, 0, 0, 0),
        A(0, 0, 1, 0, 1))

    val v = A(1,1,1,1,1)


    val snippet = new DslDriver[Array[Int],Array[Int]] {
      def snippet(v: Rep[Array[Int]]) = {

        def matrix_vector_prod(a0: Array[Array[Int]], v: Rep[Array[Int]]) = {
          val n = a0.length

          val a = staticData(a0)

          val v1 = NewArray[Int](n)

          for (i <- (0 until n):Range) {
              val sparse = a0(i).count(_ != 0) < 3
                if (sparse) {
                  for (j <- (0 until n):Range) {
                    v1(i) = v1(i) + a(i).apply(j) * v(j)
                  }
                } else {                  
                  for (j <- (0 until n):Rep[Range]) {
                    v1(i) = v1(i) + a(i).apply(j) * v(j)
                  }
              }
          }
          v1
        }

        val v1 = matrix_vector_prod(a, v)
        v1

      }
    }
    check("shonan-hmm1c", snippet.code)
  }


/* STEP 2: unroll if

  the code duplication of the loop body above is not very nice.
  fortunately, we can create arbitrary staging-time abstractions.
  we create an auxiliary method unrollIf that capture the
  conditional unrolling pattern in a general way.
  the mv prod function no longer needs to express the loop
  body twice.

  the generated code is identical. "abstraction without regret" ftw!
*/

  test("shonan-hmm1d") {

    val A = scala.Array

    val a =
      A(A(1, 1, 1, 1, 1), // dense
        A(0, 0, 0, 0, 0), // null
        A(0, 0, 1, 0, 0), // sparse
        A(0, 0, 0, 0, 0),
        A(0, 0, 1, 0, 1))

    val v = A(1,1,1,1,1)

    val snippet = new DslDriver[Array[Int],Array[Int]] {
      def snippet(v: Rep[Array[Int]]) = {

        def unrollIf(c:Boolean,r: Range) = new { def foreach(f: Rep[Int] => Rep[Unit]) = {
          if (c) {
            for (j <- (r.start until r.end):Range) {
              f(j)
            }
          } else {                  
            for (j <- (r.start until r.end):Rep[Range]) {
              f(j)
            }
          }
        }}


        def matrix_vector_prod(a0: Array[Array[Int]], v: Rep[Array[Int]]) = {
          val n = a0.length

          val a = staticData(a0)

          val v1 = NewArray[Int](n)

          for (i <- (0 until n):Range) {
              val sparse = a0(i).count(_ != 0) < 3
              for (j <- unrollIf(sparse, 0 until n)) {
                v1(i) = v1(i) + a(i).apply(j) * v(j)
              }
          }
          v1
        }

        val v1 = matrix_vector_prod(a, v)
        v1

      }
    }
    check("shonan-hmm1d", snippet.code)
  }


  test("shonan-hmm-live") {

    // playground for live demo

    val A = scala.Array

    val a =
      A(A(1, 1, 1, 1, 1), // dense
        A(0, 0, 0, 0, 0), // null
        A(0, 0, 1, 0, 0), // sparse
        A(0, 0, 0, 0, 0),
        A(0, 0, 1, 0, 1))

    val v = A(1,1,1,1,1)


    def matrix_vector_prod(a: Array[Array[Int]], v: Array[Int]) = {
      val n = a.length
      val v1 = new Array[Int](n)

      for (i <- (0 until n)) {
          for (j <- (0 until n)) {
            v1(i) = v1(i) + a(i)(j) * v(j)
          }
      }
      v1
    }

    val v1 = matrix_vector_prod(a, v)

    exec("shonan-hmm-live", v1.mkString(","))

    /*
    val c = new Compiler
    val f = { import c._
      compile { x => 2 * x}
    }
    f(8)*/
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
          comment("row_" + i) {
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
