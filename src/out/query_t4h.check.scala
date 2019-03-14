/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Name,Value,Flag,Name1")
    var x2 = 0
    var x3 = 0
    val x4 = new Array[Int](256)
    var x5 = 0
    val x6 = while (x5 != 256) {
      x4(x5) = -1
      x5 = x5 + 1
    }
    val x7 = new Array[java.lang.String](65536)
    val x8 = new Array[java.lang.String](65536)
    val x9 = new Array[java.lang.String](65536)
    var x10 = 0
    var x11 = 0
    val x12 = new Array[Int](65536)
    val x13 = new Array[Int](256)
    val x14 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    val x15 = x14.next(',')
    val x16 = x14.next(',')
    val x17 = x14.next('\n')
    val x18 = (0L.toInt) & 255
    val x19 = !true
    val x27 = while (x14.hasNext) {
      val x20 = x11
      x7(x20) = x14.next(',')
      x8(x20) = x14.next(',')
      x9(x20) = x14.next('\n')
      x11 = x11 + 1
      val x25 = {
        //#hash_lookup
        // generated code for hash lookup
        var x21 = x18
        val x23 = while ({
          x4(x21) != -1 && {
            val x22 = x4(x21)
            x19
          }
        }) {
          x21 = (x21 + 1) & 255
        }
        if (x4(x21) == -1) {
          val x24 = x3
          x3 = x3 + 1
          x4(x21) = x24
          x13(x24) = 0
          x24
        } else x4(x21)//#hash_lookup
      }
      val x26 = x13(x25)
      x12(x25 * 256 + x26) = x20
      x13(x25) = x26 + 1
    }
    val x28 = x14.close
    val x29 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    val x30 = x29.next(',')
    val x31 = x29.next(',')
    val x32 = x29.next('\n')
    val x47 = while (x29.hasNext) {
      val x33 = x29.next(',')
      val x34 = x29.next(',')
      val x35 = x29.next('\n')
      val x39 = {
        //#hash_lookup
        // generated code for hash lookup
        var x36 = x18
        val x38 = while ({
          x4(x36) != -1 && {
            val x37 = x4(x36)
            x19
          }
        }) {
          x36 = (x36 + 1) & 255
        }
        x4(x36)//#hash_lookup
      }
      val x46 = if (x39 != -1) {
        val x40 = x39 * 256
        val x41 = x40 + x13(x39)
        var x42 = x40
        val x45 = while (x42 != x41) {
          val x43 = x12(x42)
          val x44 = printf("%s,%s,%s,%s\n", x7(x43), x8(x43), x9(x43), x33)
          x42 = x42 + 1
        }
      }
    }
    val x48 = x29.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
