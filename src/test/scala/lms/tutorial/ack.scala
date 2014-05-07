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

class AckermannTest extends TutorialFunSuite {
  val under = "ack"
  def specialize(m: Int): DslDriver[Int,Int] = new DslDriver[Int,Int] with Ackermann {
    def snippet(n: Rep[Int]): Rep[Int] = a(m)(n)
  }
  test("specialize ackermann to m=2") {
    val ack2 = specialize(2)
    check("m2", ack2.code)
  }
}
