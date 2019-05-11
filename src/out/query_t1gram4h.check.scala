/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Word,Value,Word,Year,MatchCount,VolumeCount")
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
    var x7 = 0
    val x8 = new Array[Int](65536)
    val x9 = new Array[Int](256)
    val x10 = new scala.lms.tutorial.Scanner("src/data/words.csv")
    x10.next(',')
    x10.next('\n')
    while (x10.hasNext) {
      val x11 = x10.next(',')
      val x12 = x7
      x5(x12) = x11
      x6(x12) = x10.next('\n')
      x7 = x7 + 1
      val x13 = x11.hashCode.toInt & 255
      //# hash_lookup
      // generated code for hash lookup
      val x14 = {
        var x15 = x13
        while (x3(x15) != -1 && !(x1(x3(x15)) == x11)) {
          x15 = x15 + 1 & 255
        }
        if (x3(x15) == -1) {
          val x16 = x2
          x1(x16) = x11
          x2 = x2 + 1
          x3(x15) = x16
          x9(x16) = 0
          x16
        } else x3(x15)
      }
      //# hash_lookup
      val x17 = x9(x14)
      x8(x14 * 256 + x17) = x12
      x9(x14) = x17 + 1
    }
    x10.close
    val x18 = new scala.lms.tutorial.Scanner("src/data/t1gram.csv")
    while (x18.hasNext) {
      val x19 = x18.next('\t')
      val x20 = x18.next('\t')
      val x21 = x18.next('\t')
      val x22 = x18.next('\n')
      val x23 = x19.hashCode.toInt & 255
      //# hash_lookup
      // generated code for hash lookup
      val x24 = {
        var x25 = x23
        while (x3(x25) != -1 && !(x1(x3(x25)) == x19)) {
          x25 = x25 + 1 & 255
        }
        x3(x25)
      }
      //# hash_lookup
      if (x24 != -1) {
        val x26 = x24 * 256
        val x27 = x26 + x9(x24)
        var x28 = x26
        while (x28 != x27) {
          val x29 = x8(x28)
          printf("%s,%s,%s,%s,%s,%s\n", x5(x29), x6(x29), x19, x20, x21, x22)
          x28 = x28 + 1
        }
      }
    }
    x18.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
