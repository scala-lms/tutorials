/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((java.lang.String)=>(Boolean)) {
  def apply(x0:java.lang.String): Boolean = {
    var x1: Int = -1
    var x2: Boolean = false
    val x6 = x0.length
    val x57 = while ({val x3 = x2
      val x9 = if (x3) {
        false
      } else {
        val x5 = x1
        val x7 = x5 < x6
        x7
      }
      x9}) {
      val x11 = x1 += 1
      val x12 = x1
      var x13: Int = x12
      val x14 = x13
      val x15 = x14 < x6
      val x18 = if (x15) {
        val x16 = x0(x14)
        val x17 = 'b' == x16
        x17
      } else {
        false
      }
      val x20 = x18
      var x21: Boolean = x20
      var x22: Boolean = false
      val x49 = while ({val x23 = x22
        val x28 = if (x23) {
          false
        } else {
          val x25 = x21
          val x26 = !x25
          x26
        }
        val x32 = if (x28) {
          val x29 = x13
          val x30 = x29 < x6
          x30
        } else {
          false
        }
        x32}) {
        val x34 = x13
        val x35 = x0(x34)
        val x36 = 'a' == x35
        val x37 = !x36
        x22 = x37
        val x39 = x13 += 1
        val x40 = x13
        val x41 = x40 < x6
        val x44 = if (x41) {
          val x42 = x0(x40)
          val x43 = 'b' == x42
          x43
        } else {
          false
        }
        val x46 = x44
        x21 = x46
        ()
      }
      val x50 = x22
      val x54 = if (x50) {
        false
      } else {
        val x52 = x21
        x52
      }
      x2 = x54
      ()
    }
    val x58 = x2
    x58
  }
}
/*****************************************
End of Generated Code
*******************************************/
