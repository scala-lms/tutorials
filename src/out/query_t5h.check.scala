/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Name,Value,Flag,Name")
    val x1 = new Array[java.lang.String](256)
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
    val x9 = new Array[Int](65536)
    val x10 = new Array[Int](256)
    val x11 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x11.next(',')
    x11.next(',')
    x11.next('\n')
    while (x11.hasNext) {
      val x12 = x11.next(',')
      val x13 = x8
      x5(x13) = x12
      x6(x13) = x11.next(',')
      x7(x13) = x11.next('\n')
      x8 = x8 + 1
      val x14 = x12.hashCode.toInt & 255
      //# hash_lookup
      // generated code for hash lookup
      val x15 = {
        var x16 = x14
        while (x3(x16) != -1 && !(x1(x3(x16)) == x12)) {
          x16 = x16 + 1 & 255
        }
        if (x3(x16) == -1) {
          val x17 = x2
          x1(x17) = x12
          x2 = x2 + 1
          x3(x16) = x17
          x10(x17) = 0
          x17
        } else x3(x16)
      }
      //# hash_lookup
      val x18 = x10(x15)
      x9(x15 * 256 + x18) = x13
      x10(x15) = x18 + 1
    }
    x11.close
    val x19 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x19.next(',')
    x19.next(',')
    x19.next('\n')
    while (x19.hasNext) {
      val x20 = x19.next(',')
      x19.next(',')
      x19.next('\n')
      val x21 = x20.hashCode.toInt & 255
      //# hash_lookup
      // generated code for hash lookup
      val x22 = {
        var x23 = x21
        while (x3(x23) != -1 && !(x1(x3(x23)) == x20)) {
          x23 = x23 + 1 & 255
        }
        x3(x23)
      }
      //# hash_lookup
      if (x22 != -1) {
        val x24 = x22 * 256
        val x25 = x24 + x10(x22)
        var x26 = x24
        while (x26 != x25) {
          val x27 = x9(x26)
          printf("%s,%s,%s,%s\n", x5(x27), x6(x27), x7(x27), x20)
          x26 = x26 + 1
        }
      }
    }
    x19.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
