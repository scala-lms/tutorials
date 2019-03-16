/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Name,Value,Flag,Name")
    val x1 = new Array[java.lang.String](256)
    var x2 = 0
    var x3 = 0
    val x4 = new Array[Int](256)
    var x5 = 0
    while (x5 != 256) {
      x4(x5) = -1
      x5 = x5 + 1
    }
    val x6 = new Array[java.lang.String](65536)
    val x7 = new Array[java.lang.String](65536)
    val x8 = new Array[java.lang.String](65536)
    var x9 = 0
    var x10 = 0
    val x11 = new Array[Int](65536)
    val x12 = new Array[Int](256)
    val x13 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x13.next(',')
    x13.next(',')
    x13.next('\n')
    val x14 = 0L * 41L
    while (x13.hasNext) {
      val x15 = x13.next(',')
      val x16 = x10
      x6(x16) = x15
      x7(x16) = x13.next(',')
      x8(x16) = x13.next('\n')
      x10 = x10 + 1
      val x17 = ((x14 + x15.hashCode).toInt) & 255
      val x18 = {
        //#hash_lookup
        // generated code for hash lookup
        var x19 = x17
        while (x4(x19) != -1 && {
          val x20 = x1(x4(x19))
          !(true && x20 == x15)
        }) x19 = (x19 + 1) & 255
        if (x4(x19) == -1) {
          val x21 = x3
          x1(x21) = x15
          x3 = x3 + 1
          x4(x19) = x21
          x12(x21) = 0
          x21
        } else x4(x19)
        //#hash_lookup
      }
      val x22 = x12(x18)
      x11(x18 * 256 + x22) = x16
      x12(x18) = x22 + 1
    }
    x13.close
    val x23 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x23.next(',')
    x23.next(',')
    x23.next('\n')
    while (x23.hasNext) {
      val x24 = x23.next(',')
      x23.next(',')
      x23.next('\n')
      val x25 = ((x14 + x24.hashCode).toInt) & 255
      val x26 = {
        //#hash_lookup
        // generated code for hash lookup
        var x27 = x25
        while (x4(x27) != -1 && {
          val x28 = x1(x4(x27))
          !(true && x28 == x24)
        }) x27 = (x27 + 1) & 255
        x4(x27)
        //#hash_lookup
      }
      if (x26 != -1) {
        val x29 = x26 * 256
        val x30 = x29 + x12(x26)
        var x31 = x29
        while (x31 != x30) {
          val x32 = x11(x31)
          printf("%s,%s,%s,%s\n", x6(x32), x7(x32), x8(x32), x24)
          x31 = x31 + 1
        }
      } else {
      }
    }
    x23.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
