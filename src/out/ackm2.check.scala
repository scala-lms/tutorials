/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((Int)=>(Int)) {
  def apply(x0:Int): Int = {
    var x4 = null.asInstanceOf[scala.Function1[Int, Int]]
    var x1 = null.asInstanceOf[scala.Function1[Int, Int]]
    val x7 = {x8: (Int) =>
      val x9 = x8 + 1
      x9: Int
    }
    val x10 = x7(1)
    x4 = {x5: (Int) =>
      val x6 = x5 == 0
      val x15 = if (x6) {
        x10
      } else {
        val x11 = x5 - 1
        val x12 = x4(x11)
        val x13 = x7(x12)
        x13
      }
      x15: Int
    }
    x1 = {x2: (Int) =>
      val x3 = x2 == 0
      val x23 = if (x3) {
        val x17 = x4(1)
        x17
      } else {
        val x19 = x2 - 1
        val x20 = x1(x19)
        val x21 = x4(x20)
        x21
      }
      x23: Int
    }
    val x25 = x1(x0)
    x25
  }
}
/*****************************************
End of Generated Code
*******************************************/
