/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (java.lang.String => Unit) {
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
    val x13 = 0 & 255
    while (x12.hasNext) {
      val x14 = x9
      x5(x14) = x12.next(',')
      x6(x14) = x12.next(',')
      x7(x14) = x12.next('\n')
      x9 = x9 + 1
      //# hash_lookup
      // generated code for hash lookup
      val x15 = {
        var x16 = x13
        while (x3(x16) != -1 && {
          false
        }) {
          x16 = x16 + 1 & 255
        }
        if (x3(x16) == -1) {
          val x17 = x2
          x2 = x2 + 1
          x3(x16) = x17
          x11(x17) = 0
          x17
        } else x3(x16)
      }
      //# hash_lookup
      val x18 = x11(x15)
      x10(x15 * 256 + x18) = x14
      x11(x15) = x18 + 1
    }
    x12.close
    val x19 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x19.next(',')
    x19.next(',')
    x19.next('\n')
    while (x19.hasNext) {
      val x20 = x19.next(',')
      x19.next(',')
      x19.next('\n')
      //# hash_lookup
      // generated code for hash lookup
      val x21 = {
        var x22 = x13
        while (x3(x22) != -1 && {
          false
        }) {
          x22 = x22 + 1 & 255
        }
        x3(x22)
      }
      //# hash_lookup
      if (x21 != -1) {
        val x23 = x21 * 256
        val x24 = x23 + x11(x21)
        var x25 = x23
        while (x25 != x24) {
          val x26 = x10(x25)
          printf("%s,%s,%s,%s\n", x5(x26), x6(x26), x7(x26), x20)
          x25 = x25 + 1
        }
      }
    }
    x19.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
