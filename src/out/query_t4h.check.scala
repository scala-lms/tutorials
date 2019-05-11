/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Name,Value,Flag,Name1")
    var x1 = 0
    val x2 = new Array[Int](256)
    var x3 = 0
    while (x3 != 256) {
      x2(x3) = -1
      x3 = x3 + 1
    }
    val x4 = new Array[java.lang.String](65536)
    val x5 = new Array[java.lang.String](65536)
    val x6 = new Array[java.lang.String](65536)
    var x7 = 0
    val x8 = new Array[Int](65536)
    val x9 = new Array[Int](256)
    val x10 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x10.next(',')
    x10.next(',')
    x10.next('\n')
    val x11 = 0 & 255
    while (x10.hasNext) {
      val x12 = x7
      x4(x12) = x10.next(',')
      x5(x12) = x10.next(',')
      x6(x12) = x10.next('\n')
      x7 = x7 + 1
      //# hash_lookup
      // generated code for hash lookup
      val x13 = {
        var x14 = x11
        while (x2(x14) != -1 && false) {
          x14 = x14 + 1 & 255
        }
        if (x2(x14) == -1) {
          val x15 = x1
          x1 = x1 + 1
          x2(x14) = x15
          x9(x15) = 0
          x15
        } else x2(x14)
      }
      //# hash_lookup
      val x16 = x9(x13)
      x8(x13 * 256 + x16) = x12
      x9(x13) = x16 + 1
    }
    x10.close
    val x17 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x17.next(',')
    x17.next(',')
    x17.next('\n')
    while (x17.hasNext) {
      val x18 = x17.next(',')
      x17.next(',')
      x17.next('\n')
      //# hash_lookup
      // generated code for hash lookup
      val x19 = {
        var x20 = x11
        while (x2(x20) != -1 && false) {
          x20 = x20 + 1 & 255
        }
        x2(x20)
      }
      //# hash_lookup
      if (x19 != -1) {
        val x21 = x19 * 256
        val x22 = x21 + x9(x19)
        var x23 = x21
        while (x23 != x22) {
          val x24 = x8(x23)
          printf("%s,%s,%s,%s\n", x4(x24), x5(x24), x6(x24), x18)
          x23 = x23 + 1
        }
      }
    }
    x17.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
