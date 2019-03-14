/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Name,Value")
    val x2 = new Array[java.lang.String](256)
    var x3 = 0
    var x4 = 0
    val x5 = new Array[Int](256)
    var x6 = 0
    val x7 = while (x6 != 256) {
      x5(x6) = -1
      x6 = x6 + 1
    }
    val x8 = new Array[Int](256)
    var x9 = 0
    val x10 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    val x11 = x10.next(',')
    val x12 = x10.next(',')
    val x13 = x10.next('\n')
    val x14 = 0L * 41L
    val x24 = while (x10.hasNext) {
      val x15 = x10.next(',')
      val x16 = x10.next(',')
      val x17 = x10.next('\n')
      val x18 = ((x14 + x15.hashCode).toInt) & 255
      val x23 = {
        //#hash_lookup
        // generated code for hash lookup
        var x19 = x18
        val x21 = while ({
          x5(x19) != -1 && {
            val x20 = x2(x5(x19))
            !(true && x20 == x15)
          }
        }) {
          x19 = (x19 + 1) & 255
        }
        if (x5(x19) == -1) {
          val x22 = x4
          x2(x22) = x15
          x4 = x4 + 1
          x5(x19) = x22
          x8(x22) = 0
          x22
        } else x5(x19)//#hash_lookup
      }
      x8(x23) = x8(x23) + x16.toInt
    }
    val x25 = x10.close
    val x26 = x4
    var x27 = 0
    val x30 = while (x27 != x26) {
      val x28 = x27
      val x29 = printf("%s,%s\n", x2(x28), (x8(x28)).toString)
      x27 = x27 + 1
    }
  }
}
/*****************************************
End of Generated Code
*******************************************/
