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
    while (x5 != 256) {
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
    x14.next(',')
    x14.next(',')
    x14.next('\n')
    val x18 = (0L.toInt) & 255
    val x19 = !true
    while (x14.hasNext) {
      val x21 = x11
      x7(x21) = x14.next(',')
      x8(x21) = x14.next(',')
      x9(x21) = x14.next('\n')
      x11 = x11 + 1
      val x22 = {
        //#hash_lookup
        // generated code for hash lookup
        var x23 = x18
        while ({
          x4(x23) != -1 && {
            x4(x23)
            x19
          }
        }) {
          x23 = (x23 + 1) & 255
        }
        if (x4(x23) == -1) {
          val x26 = x3
          x3 = x3 + 1
          x4(x23) = x26
          x13(x26) = 0
          x26
        } else x4(x23)//#hash_lookup
      }
      val x27 = x13(x22)
      x12(x22 * 256 + x27) = x21
      x13(x22) = x27 + 1
    }
    x14.close
    val x29 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x29.next(',')
    x29.next(',')
    x29.next('\n')
    while (x29.hasNext) {
      val x34 = x29.next(',')
      x29.next(',')
      x29.next('\n')
      val x37 = {
        //#hash_lookup
        // generated code for hash lookup
        var x38 = x18
        while ({
          x4(x38) != -1 && {
            x4(x38)
            x19
          }
        }) {
          x38 = (x38 + 1) & 255
        }
        x4(x38)//#hash_lookup
      }
      if (x37 != -1) {
        val x42 = x37 * 256
        val x43 = x42 + x13(x37)
        var x44 = x42
        while (x44 != x43) {
          val x46 = x12(x44)
          printf("%s,%s,%s,%s\n", x7(x46), x8(x46), x9(x46), x34)
          x44 = x44 + 1
        }
      } else ()
    }
    x29.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
