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
    while (x10.hasNext) {
      val x11 = x7
      x4(x11) = x10.next(',')
      x5(x11) = x10.next(',')
      x6(x11) = x10.next('\n')
      x7 = x7 + 1
      //# hash_lookup
      // generated code for hash lookup
      val x12 = {
        var x13 = 0
        while (false) {
          x13 = x13 + 1 & 255
        }
        if (x2(x13) == -1) {
          val x14 = x1
          x1 = x1 + 1
          x2(x13) = x14
          x9(x14) = 0
          x14
        } else x2(x13)
      }
      //# hash_lookup
      val x15 = x9(x12)
      x8(x12 * 256 + x15) = x11
      x9(x12) = x15 + 1
    }
    x10.close
    val x16 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x16.next(',')
    x16.next(',')
    x16.next('\n')
    while (x16.hasNext) {
      val x17 = x16.next(',')
      x16.next(',')
      x16.next('\n')
      //# hash_lookup
      // generated code for hash lookup
      val x18 = {
        var x19 = 0
        while (false) {
          x19 = x19 + 1 & 255
        }
        x2(x19)
      }
      //# hash_lookup
      if (x18 != -1) {
        val x20 = x18 * 256
        val x21 = x20 + x9(x18)
        var x22 = x20
        while (x22 != x21) {
          val x23 = x8(x22)
          printf("%s,%s,%s,%s\n", x4(x23), x5(x23), x6(x23), x17)
          x22 = x22 + 1
        }
      }
    }
    x16.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
