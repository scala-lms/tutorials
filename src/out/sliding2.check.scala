/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((Int)=>(Array[Int])) {
  def apply(x0:Int): Array[Int] = {
    val x1 = new Array[Int](x0)
    val x20 = x0 > 0
    val x46 = if (x20) {
      val x21 = x1(0) = 8
      var x22: Int = 5
      var x23: Int = 1
      var x25 : Int = 0
      val x44 = while (x25 < x0) {
        // unrolled for k=0
        val x27 = x25 + 1
        val x28 = x27 < x0
        val x42 = if (x28) {
          // variable reads
          val x30 = x22
          val x31 = x23
          // computation
          val x33 = x25 + 2
          val x34 = 2 * x33
          val x35 = x34 + 3
          val x36 = x30 + x35
          val x37 = x1(x31) = x36
          // variable writes
          x22 = x35
          x23 = x33
          ()
        } else {
          ()
        }
        x25 = x25 + 1
      }
      x44
    } else {
      ()
    }
    x1
  }
}
/*****************************************
End of Generated Code
*******************************************/
