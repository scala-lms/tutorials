/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (Int => Int) {
  def apply(x0: Int): Int = {
    var x1: scala.Function1[Int, Int] = null.asInstanceOf[scala.Function1[Int, Int]]
    var x2: scala.Function1[Int, Int] = null.asInstanceOf[scala.Function1[Int, Int]]
    x1 = x3
    x2 = x4
    def x6(x5:Int): Int = {
      // ack_0
      x5 + 1
    }
    def x4(x7:Int): Int = {
      // ack_1
      if (x7 == 0) {
        x6(1)
      } else {
        x6(x2(x7 - 1))
      }
    }
    def x3(x8:Int): Int = {
      // ack_2
      if (x8 == 0) {
        x4(1)
      } else {
        x4(x1(x8 - 1))
      }
    }
    x3(x0)
  }
}
/*****************************************
End of Generated Code
*******************************************/
