/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Word,Value,Phrase,Year,MatchCount,VolumeCount")
    var x1 = 0
    val x2 = new Array[Int](256)
    var x3 = 0
    while (x3 != 256) {
      x2(x3) = -1
      x3 = x3 + 1
    }
    val x4 = new Array[java.lang.String](65536)
    val x5 = new Array[java.lang.String](65536)
    var x6 = 0
    val x7 = new Array[Int](65536)
    val x8 = new Array[Int](256)
    val x9 = new scala.lms.tutorial.Scanner("src/data/words.csv")
    x9.next(',')
    x9.next('\n')
    while (x9.hasNext) {
      val x10 = x6
      x4(x10) = x9.next(',')
      x5(x10) = x9.next('\n')
      x6 = x6 + 1
      //# hash_lookup
      // generated code for hash lookup
      val x11 = {
        var x12 = 0
        while (false) {
          x12 = x12 + 1 & 255
        }
        if (x2(x12) == -1) {
          val x13 = x1
          x1 = x1 + 1
          x2(x12) = x13
          x8(x13) = 0
          x13
        } else x2(x12)
      }
      //# hash_lookup
      val x14 = x8(x11)
      x7(x11 * 256 + x14) = x10
      x8(x11) = x14 + 1
    }
    x9.close
    val x15 = new scala.lms.tutorial.Scanner("src/data/t1gram.csv")
    while (x15.hasNext) {
      val x16 = x15.next('\t')
      val x17 = x15.next('\t')
      val x18 = x15.next('\t')
      val x19 = x15.next('\n')
      //# hash_lookup
      // generated code for hash lookup
      val x20 = {
        var x21 = 0
        while (false) {
          x21 = x21 + 1 & 255
        }
        x2(x21)
      }
      //# hash_lookup
      if (x20 != -1) {
        val x22 = x20 * 256
        val x23 = x22 + x8(x20)
        var x24 = x22
        while (x24 != x23) {
          val x25 = x7(x24)
          printf("%s,%s,%s,%s,%s,%s\n", x4(x25), x5(x25), x16, x17, x18, x19)
          x24 = x24 + 1
        }
      }
    }
    x15.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
