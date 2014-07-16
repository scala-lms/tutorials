/** 

Shonan Challenge
================

In this tutorial we will develop a solution to the HMM problem from the Shonan Challenge.

  For further reference, see:

    - Shonan Challenge for Generative Programming
      Baris Aktemur, Yukiyoshi Kameyama, Oleg Kiselyov, Chung-chieh Shan. PEPM'13
      https://www.cs.rutgers.edu/~ccshan/metafx/pepm2013.pdf

    - https://github.com/StagedHPC/shonan-challenge

*/

package scala.lms.tutorial

import scala.virtualization.lms.common._
import scala.reflect.SourceContext

class ShonanTest extends TutorialFunSuite {
  val under = "dslapi"
  val A = scala.Array

/**
We first define our static matrix. We notice that some rows are dense, 
some rows are sparse, and some rows are fully zero.
*/

  val a =
    A(A(1, 1, 1, 1, 1), // dense
      A(0, 0, 0, 0, 0), // null
      A(0, 0, 1, 0, 0), // sparse
      A(0, 0, 0, 0, 0),
      A(0, 0, 1, 0, 1))

/**
STEP 0: starting point
----------------------

We start with a fully static implementation.
*/

  test("shonan-hmm1a") {

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

    // let's run it on some static input:

    val v = A(1,1,1,1,1)

    val v1 = matrix_vector_prod(a, v)
    val result = v1.mkString(",")

    check("shonan-hmm1a", result)
  }
/**
STEP 0.5: static vs dynamic conditional
---------------------------------------

Now we're adding some basic codegen facilities and play
with static vs dynamic expressions. Fully static expressions
do not show up in generated code. If we change the 
condition to depend on the (dynamic) input array, an if/else
statement will be generated.
*/

  test("shonan-hmm1b") {
    val snippet = new DslDriver[Array[Int],Array[Int]] {
      def snippet(v: Rep[Array[Int]]) = {

        val x = 100 - 5 //* v0.length

        if (x > 10) {
          println("hello")
        }

        v
      }
    }
    check("shonan-hmm1b", snippet.code)
  }

/**
STEP 1: Staging the matrix vector product
-----------------------------------------

We want to change the mv prod function to take a dynamic vector
and a static matrix. We move it into the DslDriver trait so
it can use Rep.

We generate nested loops by default (Rep[Range]). To make that
work, we need to lift the static matrix to a Rep value. We
do that using `staticData`.

Now we can play with the loops shapes: Range loops are computed
statically, i.e. unrolled at staging time. Rep[Range] loops cause
generation of loop code. 

We can make unrolling decisions in a very fine-grained manner:
for dense rows we want to generate a loop, for sparse rows
we want to unroll. We just add a condition `sparse` and 
either do a Range or Rep[Range] loop.
*/

  test("shonan-hmm1c") {
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
    //check("shonan-hmm1c", snippet.code)
  }

/**
STEP 2: Conditional unrolling
-----------------------------

The code duplication of the loop body above is not very nice.
fortunately, we can create arbitrary staging-time abstractions.
We create an auxiliary method unrollIf that captures the
conditional unrolling pattern in a general way.
The mv prod function no longer needs to express the loop
body twice.

The generated code is identical. "abstraction without regret" ftw!
*/

  test("shonan-hmm1d") {
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
    //check("shonan-hmm1d", snippet.code)
  }
}
