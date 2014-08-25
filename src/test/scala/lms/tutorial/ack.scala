/**
Ackermann's Function
====================

By selectively specializing on a subset of the argument, we can turn
a recursive function into an automaton, i.e. a set of mutually recursive 
functions.

Outline:
<div id="tableofcontents"></div>

*/

package scala.lms.tutorial

import org.scalatest.FunSuite

trait Ackermann extends Dsl {
  def a(m: Int): Rep[Int => Int] = fun { (n: Rep[Int]) =>
    if (m==0)
      n+1
    else if (n==0)
      a(m-1)(1)
    else
      a(m-1)(a(m)(n-1))
  }
}

/**
  The example is due to Neil Jones, via Oleg on [LtU](http://lambda-the-ultimate.org/node/4039#comment-61431).

  ack(2,n) should specialize to:

  ack_2(n) =  if n=0 then ack_1(1) else  ack_1(ack_2(n-1))
  ack_1(n) =  if n=0 then ack_0(1) else  ack_0(ack_1(n-1))
  ack_0(n) =  n+1
*/
class AckermannTest extends TutorialFunSuite {
  val under = "ack"
  def specialize(m: Int): DslDriver[Int,Int] = new DslDriver[Int,Int] with Ackermann {
    def snippet(n: Rep[Int]): Rep[Int] = a(m)(n)
  }

  test("specialize ackermann to m=2") {
    val ack2 = specialize(2)
    check("m2", ack2.code)
  }
/**
      .. includecode:: ../../../../out/ackm2.check.scala

/**
What's next?
------------

Go back to the [tutorial index](index.html) or continue with the [automata-based regex matcher](automata.html).
*/

}
