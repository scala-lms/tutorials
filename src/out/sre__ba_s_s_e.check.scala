/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((java.lang.String)=>(Boolean)) {
  def apply(x0:java.lang.String): Boolean = {
    var x1: Int = 0
    val x2 = x0.length
    val x3 = 0 < x2
    val x6 = if (x3) {
      val x4 = x0(0)
      val x5 = '*' == x4
      x5
    } else {
      false
    }
    val x8 = if (x6) {
      val x7 = 1 == x2
      x7
    } else {
      false
    }
    var x9: Boolean = x8
    var x10: Boolean = false
    val x38 = while ({val x11 = x10
      val x16 = if (x11) {
        false
      } else {
        val x13 = x9
        val x14 = !x13
        x14
      }
      val x20 = if (x16) {
        val x17 = x1
        val x18 = x17 < x2
        x18
      } else {
        false
      }
      x20}) {
      val x22 = x1
      val x23 = x0(x22)
      val x24 = 'a' == x23
      val x25 = !x24
      x10 = x25
      val x27 = x1 += 1
      val x28 = x1
      val x29 = x28 < x2
      val x32 = if (x29) {
        val x30 = x0(x28)
        val x31 = '*' == x30
        x31
      } else {
        false
      }
      val x35 = if (x32) {
        val x33 = x28 + 1
        val x34 = x33 == x2
        x34
      } else {
        false
      }
      x9 = x35
      ()
    }
    val x39 = x10
    val x43 = if (x39) {
      false
    } else {
      val x41 = x9
      x41
    }
    x43
  }
}
/*****************************************
End of Generated Code
*******************************************/
