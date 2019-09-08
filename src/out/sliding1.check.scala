/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((Int)=>(Array[Int])) {
  def apply(x0:Int): Array[Int] = {
    val x1 = new Array[Int](x0)
    val x20 = x0 > 0
    val x42 = if (x20) {
      val x21 = x1(0) = 8
      var x22: Int = 5
      var x23: Int = 1
      var x25 : Int = 1
      val x40 = while (x25 < x0) {
        // variable reads
        val x27 = x22
        val x28 = x23
        // computation
        val x31 = x25 + 1
        val x32 = 2 * x31
        val x33 = x32 + 3
        val x34 = x27 + x33
        val x35 = x1(x28) = x34
        // variable writes
        x22 = x33
        x23 = x31
        x25 = x25 + 1
      }
      x40
    } else {
      ()
    }
    x1
  }
}
/*****************************************
End of Generated Code
*******************************************/
