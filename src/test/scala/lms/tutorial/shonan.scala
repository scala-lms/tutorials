package scala.lms.tutorial

import scala.virtualization.lms.common._
import scala.reflect.SourceContext

// sbt ~test-only *Shonan*

class ShonanTest extends TutorialFunSuite {
  val under = "dslapi"

/*  playground for live demo */

  test("shonan-hmm-live") {
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
          v1(i) = v1(i) + a(i).apply(j) * v(j)
        }
      }
      v1
    }

    val v1 = matrix_vector_prod(a, v)

    exec("shonan-hmm-live", v1.mkString(","))
  }




  /*
    val snippet = new DslDriver[Array[Int],Array[Int]] {
      def snippet(v: Rep[Array[Int]]) = {
        println("hello")
        v
      }
    }
    exec("shonan-hmm-live", snippet.code)
  */



/*
  DEMO:
  1) add compile snippet
  2) add conditional
  3) stage mv prod
      - staticData(a)
      - NewArray[Int](n)     
  4) Range vs Rep[Range]
  5) unrollIf
*/



//#hmm1a_doc
/*
STEP 0: starting point

  this is our static implementation for the HMM shonan challenge problem.

  for further reference, see:

    - Shonan Challenge for Generative Programming
      Baris Aktemur, Yukiyoshi Kameyama, Oleg Kiselyov, Chung-chieh Shan. PEPM'13
      https://www.cs.rutgers.edu/~ccshan/metafx/pepm2013.pdf

    - https://github.com/StagedHPC/shonan-challenge
*/
//#hmm1a_doc
  test("shonan-hmm1a") {
//#hmm1a
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
    val result = v1.mkString(",")
//#hmm1a
    check("shonan-hmm1a", result)
  }

//#hmm1b_doc
/* STEP 0.5: static vs dynamic conditional

  now we're adding some basic codegen facilities and play
  with static vs dynamic expressions. fully static expressions
  do not show up in generated code. if we change the 
  condition to depend on the (dynamic) input array, an if/else
  statement will be generated.
*/
//#hmm1b_doc
  test("shonan-hmm1b") {
//#hmm1b
    val snippet = new DslDriver[Array[Int],Array[Int]] {
      def snippet(v: Rep[Array[Int]]) = {

        val x = 100 - 5 //* v0.length

        if (x > 10) {
          println("hello")
        }

        v
 
      }
    }
//#hmm1b
    check("shonan-hmm1b", snippet.code)
  }


//#hmm1c_doc
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
//#hmm1c_doc
  test("shonan-hmm1c") {
//#hmm1c
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
//#hmm1c
    check("shonan-hmm1c", snippet.code)
  }

//#hmm1d_doc
/* STEP 2: unroll if

  the code duplication of the loop body above is not very nice.
  fortunately, we can create arbitrary staging-time abstractions.
  we create an auxiliary method unrollIf that captures the
  conditional unrolling pattern in a general way.
  the mv prod function no longer needs to express the loop
  body twice.

  the generated code is identical. "abstraction without regret" ftw!
*/
//#hmm1d_doc
  test("shonan-hmm1d") {
//#hmm1d
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
//#hmm1d
    check("shonan-hmm1d", snippet.code)
  }


/*  sphinx-instrumented code for website */

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
