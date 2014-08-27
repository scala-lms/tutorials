/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((java.lang.String)=>(Boolean)) {
  def apply(x0:java.lang.String): Boolean = {
    var x1: Int = -1
    var x2: Boolean = false
    val x6 = x0.length
    val x60 = while ({val x3 = x2
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
      val x13 = x12 < x6
      val x16 = if (x13) {
        val x14 = x0(x12)
        val x15 = 'h' == x14
        x15
      } else {
        false
      }
      val x57 = if (x16) {
        val x17 = x12 + 1
        val x18 = x17 < x6
        val x21 = if (x18) {
          val x19 = x0(x17)
          val x20 = 'e' == x19
          x20
        } else {
          false
        }
        val x56 = if (x21) {
          val x22 = x17 + 1
          var x23: Int = x22
          val x24 = x23
          val x25 = x24 == x6
          var x26: Boolean = x25
          var x27: Boolean = false
          val x49 = while ({val x28 = x27
            val x33 = if (x28) {
              false
            } else {
              val x30 = x26
              val x31 = !x30
              x31
            }
            val x37 = if (x33) {
              val x34 = x23
              val x35 = x34 < x6
              x35
            } else {
              false
            }
            x37}) {
            val x39 = x23
            val x40 = x0(x39)
            val x41 = 'l' == x40
            val x42 = !x41
            x27 = x42
            val x44 = x23 += 1
            val x45 = x23
            val x46 = x45 == x6
            x26 = x46
            ()
          }
          val x50 = x27
          val x54 = if (x50) {
            false
          } else {
            val x52 = x26
            x52
          }
          x54
        } else {
          false
        }
        x56
      } else {
        false
      }
      x2 = x57
      ()
    }
    val x61 = x2
    x61
  }
}
/*****************************************
End of Generated Code
*******************************************/
