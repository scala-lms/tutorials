/*****************************************
Emitting Generated Code
*******************************************/
class Snippet(px1: Array[Int]) extends (Array[Int] => Array[Int]) {
  def apply(x0: Array[Int]): Array[Int] = {
    val x2 = new Array[Int](5)
    var x3 = 0
    val x1 = px1 /* staticData Array(1,1,1,1,1) */
    while (x3 != 5) {
      val x4 = x3
      x2(0) = x2(0) + x1(x4) * x0(x4)
      x3 = x3 + 1
    }
    x2(2) = x2(2) + x0(2)
    x2(4) = x2(4) + x0(2) + x0(4)
    x2
  }
}
/*****************************************
End of Generated Code
*******************************************/
