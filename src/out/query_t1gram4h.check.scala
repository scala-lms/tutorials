/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Word,Value,Word,Year,MatchCount,VolumeCount")
    val x2 = new Array[java.lang.String](256)
    var x3 = 0
    var x4 = 0
    val x5 = new Array[Int](256)
    var x6 = 0
    while (x6 != 256) {
      x5(x6) = -1
      x6 = x6 + 1
    }
    val x8 = new Array[java.lang.String](65536)
    val x9 = new Array[java.lang.String](65536)
    var x10 = 0
    var x11 = 0
    val x12 = new Array[Int](65536)
    val x13 = new Array[Int](256)
    val x14 = new scala.lms.tutorial.Scanner("src/data/words.csv")
    x14.next(',')
    x14.next('\n')
    val x17 = 0L * 41L
    while (x14.hasNext) {
      val x19 = x14.next(',')
      val x20 = x11
      x8(x20) = x19
      x9(x20) = x14.next('\n')
      x11 = x11 + 1
      val x21 = ((x17 + x19.hashCode).toInt) & 255
      val x22 = {
        //#hash_lookup
        // generated code for hash lookup
        var x23 = x21
        while ({
          x5(x23) != -1 && {
            val x25 = x2(x5(x23))
            !(true && x25 == x19)
          }
        }) {
          x23 = (x23 + 1) & 255
        }
        if (x5(x23) == -1) {
          val x26 = x4
          x2(x26) = x19
          x4 = x4 + 1
          x5(x23) = x26
          x13(x26) = 0
          x26
        } else x5(x23)//#hash_lookup
      }
      val x27 = x13(x22)
      x12(x22 * 256 + x27) = x20
      x13(x22) = x27 + 1
    }
    x14.close
    val x29 = new scala.lms.tutorial.Scanner(x0)
    while (x29.hasNext) {
      val x31 = x29.next('\t')
      val x32 = x29.next('\t')
      val x33 = x29.next('\t')
      val x34 = x29.next('\n')
      val x35 = ((x17 + x31.hashCode).toInt) & 255
      val x36 = {
        //#hash_lookup
        // generated code for hash lookup
        var x37 = x35
        while ({
          x5(x37) != -1 && {
            val x39 = x2(x5(x37))
            !(true && x39 == x31)
          }
        }) {
          x37 = (x37 + 1) & 255
        }
        x5(x37)//#hash_lookup
      }
      if (x36 != -1) {
        val x41 = x36 * 256
        val x42 = x41 + x13(x36)
        var x43 = x41
        while (x43 != x42) {
          val x45 = x12(x43)
          printf("%s,%s,%s,%s,%s,%s\n", x8(x45), x9(x45), x31, x32, x33, x34)
          x43 = x43 + 1
        }
      } else ()
    }
    x29.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
