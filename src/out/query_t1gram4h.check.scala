/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Word,Value,Word,Year,MatchCount,VolumeCount")
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
    var x8 = 0
    var x9 = 0
    val x10 = new Array[Int](65536)
    val x11 = new Array[Int](256)
    val x12 = new scala.lms.tutorial.Scanner("src/data/words.csv")
    x12.next(',')
    x12.next('\n')
    val x13 = 0L * 41L
    while (x12.hasNext) {
      val x14 = x12.next(',')
      val x15 = x9
      x6(x15) = x14
      x7(x15) = x12.next('\n')
      x9 = x9 + 1
      val x16 = ((x13 + x14.hashCode).toInt) & 255
      val x17 = {
        //#hash_lookup
        // generated code for hash lookup
        var x18 = x16
        while (x4(x18) != -1 && {
          val x19 = x1(x4(x18))
          !(true && x19 == x14)
        }) x18 = (x18 + 1) & 255
        if (x4(x18) == -1) {
          val x20 = x3
          x1(x20) = x14
          x3 = x3 + 1
          x4(x18) = x20
          x11(x20) = 0
          x20
        } else x4(x18)
        //#hash_lookup
      }
      val x21 = x11(x17)
      x10(x17 * 256 + x21) = x15
      x11(x17) = x21 + 1
    }
    x12.close
    val x22 = new scala.lms.tutorial.Scanner(x0)
    while (x22.hasNext) {
      val x23 = x22.next('\t')
      val x24 = x22.next('\t')
      val x25 = x22.next('\t')
      val x26 = x22.next('\n')
      val x27 = ((x13 + x23.hashCode).toInt) & 255
      val x28 = {
        //#hash_lookup
        // generated code for hash lookup
        var x29 = x27
        while (x4(x29) != -1 && {
          val x30 = x1(x4(x29))
          !(true && x30 == x23)
        }) x29 = (x29 + 1) & 255
        x4(x29)
        //#hash_lookup
      }
      if (x28 != -1) {
        val x31 = x28 * 256
        val x32 = x31 + x11(x28)
        var x33 = x31
        while (x33 != x32) {
          val x34 = x10(x33)
          printf("%s,%s,%s,%s,%s,%s\n", x6(x34), x7(x34), x23, x24, x25, x26)
          x33 = x33 + 1
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
