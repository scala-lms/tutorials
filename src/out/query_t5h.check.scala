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
        while (x4(x19) != -1 && !(x1(x4(x19)) == x15)) x19 = (x19 + 1) & 255
        if (x4(x19) == -1) {
          val x20 = x3
          x1(x20) = x15
          x3 = x3 + 1
          x4(x19) = x20
          x12(x20) = 0
          x20
        } else x4(x19)
        //#hash_lookup
      }
      val x21 = x12(x18)
      x11(x18 * 256 + x21) = x16
      x12(x18) = x21 + 1
    }
    x13.close
    val x22 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x22.next(',')
    x22.next(',')
    x22.next('\n')
    while (x22.hasNext) {
      val x23 = x22.next(',')
      x22.next(',')
      x22.next('\n')
      val x24 = ((x14 + x23.hashCode).toInt) & 255
      val x25 = {
        //#hash_lookup
        // generated code for hash lookup
        var x26 = x24
        while (x4(x26) != -1 && !(x1(x4(x26)) == x23)) x26 = (x26 + 1) & 255
        x4(x26)
        //#hash_lookup
      }
      if (x25 != -1) {
        val x27 = x25 * 256
        val x28 = x27 + x12(x25)
        var x29 = x27
        while (x29 != x28) {
          val x30 = x11(x29)
          printf("%s,%s,%s,%s\n", x6(x30), x7(x30), x8(x30), x23)
          x29 = x29 + 1
        }
      } else {
      }
    }
    x22.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
