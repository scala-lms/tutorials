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
    val x10 = 0 & 255
    while (x9.hasNext) {
      val x11 = x6
      x4(x11) = x9.next(',')
      x5(x11) = x9.next('\n')
      x6 = x6 + 1
      //# hash_lookup
      // generated code for hash lookup
      val x12 = {
        var x13 = x10
        while (x2(x13) != -1 && false) {
          x13 = x13 + 1 & 255
        }
        if (x2(x13) == -1) {
          val x14 = x1
          x1 = x1 + 1
          x2(x13) = x14
          x8(x14) = 0
          x14
        } else x2(x13)
      }
      //# hash_lookup
      val x15 = x8(x12)
      x7(x12 * 256 + x15) = x11
      x8(x12) = x15 + 1
    }
    x9.close
    val x16 = new scala.lms.tutorial.Scanner("src/data/t1gram.csv")
    while (x16.hasNext) {
      val x17 = x16.next('\t')
      val x18 = x16.next('\t')
      val x19 = x16.next('\t')
      val x20 = x16.next('\n')
      //# hash_lookup
      // generated code for hash lookup
      val x21 = {
        var x22 = x10
        while (x2(x22) != -1 && false) {
          x22 = x22 + 1 & 255
        }
        x2(x22)
      }
      //# hash_lookup
      if (x21 != -1) {
        val x23 = x21 * 256
        val x24 = x23 + x8(x21)
        var x25 = x23
        while (x25 != x24) {
          val x26 = x7(x25)
          printf("%s,%s,%s,%s,%s,%s\n", x4(x26), x5(x26), x17, x18, x19, x20)
          x25 = x25 + 1
        }
      }
    }
    x16.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
