/**
Getting Started
===============

Staging
-------

What is staging? The idea behind staging is to delay computation of
certain expressions, generating code to compute them later. The
benefit is *abstraction without regret*.

Lightweight Modular Staging (LMS)
---------------------------------

Lightweight Modular Staging (LMS) is a staging technique driven by
types. The technique has been implemented in Scala, in a framework to
build compilers.

In addition to the *staging* aspect, the technique is *lightweight*,
because it is purely library based. It is also *modular*, for two
reasons: (1) features can be mixed and matched (2) DSL specification
and implementation are separate.
*/


package scala.lms.tutorial

import scala.virtualization.lms.common._

class DslApiTest extends TutorialFunSuite {
  val under = "dslapi"


/**
### Rep[T] vs T

In LMS, `Rep[T]` represents a delayed compuation of type `T`. Thus,
during staging, an expression of type `Rep[T]` becomes part of
the generated code, while an expression of bare type `T` becomes a
constant in the generated code. For core Scala features, adding
`Rep` types should be enough to build a program generator, as we
will see later.

Contrast the snippet on the left, where `b` is a `Boolean`, with
the snippet on the right, where `b` is a `Rep[Boolean]`. The
expression `if (b) 1 else x` is executed at staging time when `b`
is a `Boolean`, while it is delayed causing code to be generated for
the `if` expression when `b` is a `Rep[Boolean]` -- indeed, the
actual value of `b` is not known at staging time, but only when the
generated code is executed.

*/

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
/**
      .. includecode:: ../out/dslapi1.check.scala
      .. includecode:: ../out/dslapi2.check.scala
*/  


/**
### Rep[A => B] vs Rep[A]=>Rep[B]

In the previous snippets, we already notice some *abstraction without
regret*: the ``compute`` function exists only at staging time, and is
not part of the generated code -- more generally, we can freely use
abstractions to structure and compose the staged program, but these
abstractions are not part of the generated code when their type is a
bare ``T`` as opposed of a ``Rep[T]``. In the right snippet,
``compute`` has the type ``Rep[Boolean] => Rep[Int]``, not
``Rep[Boolean => Int]`` -- its type already tells us that the function
is known at staging time.
*/

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


/**
### Rep[Range] vs Range

Loops can be unrolled in the first stage, or be generated as loops in
the second stage, driven by the type of their condition.
*/

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

/**
      .. includecode:: ../out/dslapirange1.check.scala
         :include: for
      .. includecode:: ../out/dslapirange2.check.scala
         :include: for
*/


/**
### Shonan Challenge
*/

  test("shonan-hmm") {
    val snippet = new DslDriver[Array[Int],Array[Int]] {
/**
With this fine-grain control, conditional loop unrolling can be
implemented at the DSL user level.
*/
      //#unrollIf
      def unrollIf(range: Range)(cond: Boolean) = new {
        def foreach(f: Rep[Int] => Rep[Unit]): Rep[Unit] = {
          if (cond) for (i <- range) f(i)
          else for (i <- (range.start until range.end): Rep[Range]) f(i)
        }
      }
      //#unrollIf

/**
We can use this conditional loop unrolling to optimize multiplying a
matrix known at staging time with a vector. For example, consider a
matrix with a mix of dense and sparse rows:
*/
      val A = scala.Array
      val a =
        //#ex-a
        A(A(1, 1, 1, 1, 1), // dense
          A(0, 0, 0, 0, 0), // null
          A(0, 0, 1, 0, 0), // sparse
          A(0, 0, 0, 0, 0),
          A(0, 0, 1, 0, 1))
        //#ex-a


/**
When scanning the columns, we would like to generate a loop for the
dense row, and unroll the loop for the sparse rows.
*/
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

/**
Let's compare the code generated for a dense vs sparse row.
      .. includecode:: ../out/dslapishonan-hmm.check.scala
         :include: row_0
      .. includecode:: ../out/dslapishonan-hmm.check.scala
         :include: row_2
*/

}

