/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Word,Value,Word,Year,MatchCount,VolumeCount")
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
      val x18 = x14.next(',')
      val x19 = x11
      x8(x19) = x18
      x9(x19) = x14.next('\n')
      x11 = x11 + 1
      val x20 = ((x17 + x18.hashCode).toInt) & 255
      val x25 = {
        //#hash_lookup
        // generated code for hash lookup
        var x21 = x20
        while ({
          x5(x21) != -1 && {
            val x22 = x2(x5(x21))
            !(true && x22 == x18)
          }
        }) {
          x21 = (x21 + 1) & 255
        }
        if (x5(x21) == -1) {
          val x24 = x4
          x2(x24) = x18
          x4 = x4 + 1
          x5(x21) = x24
          x13(x24) = 0
          x24
        } else x5(x21)//#hash_lookup
      }
      val x26 = x13(x25)
      x12(x25 * 256 + x26) = x19
      x13(x25) = x26 + 1
    }
    x14.close
    val x29 = new scala.lms.tutorial.Scanner(x0)
    while (x29.hasNext) {
      val x30 = x29.next('\t')
      val x31 = x29.next('\t')
      val x32 = x29.next('\t')
      val x33 = x29.next('\n')
      val x34 = ((x17 + x30.hashCode).toInt) & 255
      val x38 = {
        //#hash_lookup
        // generated code for hash lookup
        var x35 = x34
        while ({
          x5(x35) != -1 && {
            val x36 = x2(x5(x35))
            !(true && x36 == x30)
          }
        }) {
          x35 = (x35 + 1) & 255
        }
        x5(x35)//#hash_lookup
      }
      if (x38 != -1) {
        val x39 = x38 * 256
        val x40 = x39 + x13(x38)
        var x41 = x39
        while (x41 != x40) {
          val x42 = x12(x41)
          printf("%s,%s,%s,%s,%s,%s\n", x8(x42), x9(x42), x30, x31, x32, x33)
          x41 = x41 + 1
        }
      }
    }
    x29.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
