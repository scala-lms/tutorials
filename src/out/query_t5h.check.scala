/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Name,Value,Flag,Name")
    val x2 = new Array[java.lang.String](256)
    var x3 = 0
    var x4 = 0
    val x5 = new Array[Int](256)
    var x6 = 0
    val x7 = while (x6 != 256) {
      x5(x6) = -1
      x6 = x6 + 1
    }
    val x8 = new Array[java.lang.String](65536)
    val x9 = new Array[java.lang.String](65536)
    val x10 = new Array[java.lang.String](65536)
    var x11 = 0
    var x12 = 0
    val x13 = new Array[Int](65536)
    val x14 = new Array[Int](256)
    val x15 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    val x16 = x15.next(',')
    val x17 = x15.next(',')
    val x18 = x15.next('\n')
    val x19 = 0L * 41L
    val x29 = while (x15.hasNext) {
      val x20 = x15.next(',')
      val x21 = x12
      x8(x21) = x20
      x9(x21) = x15.next(',')
      x10(x21) = x15.next('\n')
      x12 = x12 + 1
      val x22 = ((x19 + x20.hashCode).toInt) & 255
      val x27 = {
        //#hash_lookup
        // generated code for hash lookup
        var x23 = x22
        val x25 = while ({
          x5(x23) != -1 && {
            val x24 = x2(x5(x23))
            !(true && x24 == x20)
          }
        }) {
          x23 = (x23 + 1) & 255
        }
        if (x5(x23) == -1) {
          val x26 = x4
          x2(x26) = x20
          x4 = x4 + 1
          x5(x23) = x26
          x14(x26) = 0
          x26
        } else x5(x23)//#hash_lookup
      }
      val x28 = x14(x27)
      x13(x27 * 256 + x28) = x21
      x14(x27) = x28 + 1
    }
    val x30 = x15.close
    val x31 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    val x32 = x31.next(',')
    val x33 = x31.next(',')
    val x34 = x31.next('\n')
    val x50 = while (x31.hasNext) {
      val x35 = x31.next(',')
      val x36 = x31.next(',')
      val x37 = x31.next('\n')
      val x38 = ((x19 + x35.hashCode).toInt) & 255
      val x42 = {
        //#hash_lookup
        // generated code for hash lookup
        var x39 = x38
        val x41 = while ({
          x5(x39) != -1 && {
            val x40 = x2(x5(x39))
            !(true && x40 == x35)
          }
        }) {
          x39 = (x39 + 1) & 255
        }
        x5(x39)//#hash_lookup
      }
      val x49 = if (x42 != -1) {
        val x43 = x42 * 256
        val x44 = x43 + x14(x42)
        var x45 = x43
        val x48 = while (x45 != x44) {
          val x46 = x13(x45)
          val x47 = printf("%s,%s,%s,%s\n", x8(x46), x9(x46), x10(x46), x35)
          x45 = x45 + 1
        }
      }
    }
    val x51 = x31.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
