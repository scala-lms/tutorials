/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Name,Value,Flag,Name1")
    var x1 = 0
    var x2 = 0
    val x3 = new Array[Int](256)
    var x4 = 0
    while (x4 != 256) {
      x3(x4) = -1
      x4 = x4 + 1
    }
    val x5 = new Array[java.lang.String](65536)
    val x6 = new Array[java.lang.String](65536)
    val x7 = new Array[java.lang.String](65536)
    var x8 = 0
    var x9 = 0
    val x10 = new Array[Int](65536)
    val x11 = new Array[Int](256)
    val x12 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x12.next(',')
    x12.next(',')
    x12.next('\n')
    val x13 = (0L.toInt) & 255
    val x14 = !true
    while (x12.hasNext) {
      val x15 = x9
      x5(x15) = x12.next(',')
      x6(x15) = x12.next(',')
      x7(x15) = x12.next('\n')
      x9 = x9 + 1
      val x16 = {
        //#hash_lookup
        // generated code for hash lookup
        var x17 = x13
        while (x3(x17) != -1 && x14) x17 = (x17 + 1) & 255
        if (x3(x17) == -1) {
          val x18 = x2
          x2 = x2 + 1
          x3(x17) = x18
          x11(x18) = 0
          x18
        } else x3(x17)
        //#hash_lookup
      }
      val x19 = x11(x16)
      x10(x16 * 256 + x19) = x15
      x11(x16) = x19 + 1
    }
    x12.close
    val x20 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x20.next(',')
    x20.next(',')
    x20.next('\n')
    while (x20.hasNext) {
      val x21 = x20.next(',')
      x20.next(',')
      x20.next('\n')
      val x22 = {
        //#hash_lookup
        // generated code for hash lookup
        var x23 = x13
        while (x3(x23) != -1 && x14) x23 = (x23 + 1) & 255
        x3(x23)
        //#hash_lookup
      }
      if (x22 != -1) {
        val x24 = x22 * 256
        val x25 = x24 + x11(x22)
        var x26 = x24
        while (x26 != x25) {
          val x27 = x10(x26)
          printf("%s,%s,%s,%s\n", x5(x27), x6(x27), x7(x27), x21)
          x26 = x26 + 1
        }
      } else {
      }
    }
    x20.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
