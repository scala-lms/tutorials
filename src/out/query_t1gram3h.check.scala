/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Word,Value,Phrase,Year,MatchCount,VolumeCount")
    var x2 = 0
    var x3 = 0
    val x4 = new Array[Int](256)
    var x5 = 0
    while (x5 != 256) {
      x4(x5) = -1
      x5 = x5 + 1
    }
    val x7 = new Array[java.lang.String](65536)
    val x8 = new Array[java.lang.String](65536)
    var x9 = 0
    var x10 = 0
    val x11 = new Array[Int](65536)
    val x12 = new Array[Int](256)
    val x13 = new scala.lms.tutorial.Scanner("src/data/words.csv")
    x13.next(',')
    x13.next('\n')
    val x16 = (0L.toInt) & 255
    val x17 = !true
    while (x13.hasNext) {
      val x19 = x10
      x7(x19) = x13.next(',')
      x8(x19) = x13.next('\n')
      x10 = x10 + 1
      val x20 = {
        //#hash_lookup
        // generated code for hash lookup
        var x21 = x16
        while ({
          x4(x21) != -1 && {
            x4(x21)
            x17
          }
        }) {
          x21 = (x21 + 1) & 255
        }
        if (x4(x21) == -1) {
          val x24 = x3
          x3 = x3 + 1
          x4(x21) = x24
          x12(x24) = 0
          x24
        } else x4(x21)//#hash_lookup
      }
      val x25 = x12(x20)
      x11(x20 * 256 + x25) = x19
      x12(x20) = x25 + 1
    }
    x13.close
    val x27 = new scala.lms.tutorial.Scanner(x0)
    while (x27.hasNext) {
      val x29 = x27.next('\t')
      val x30 = x27.next('\t')
      val x31 = x27.next('\t')
      val x32 = x27.next('\n')
      val x33 = {
        //#hash_lookup
        // generated code for hash lookup
        var x34 = x16
        while ({
          x4(x34) != -1 && {
            x4(x34)
            x17
          }
        }) {
          x34 = (x34 + 1) & 255
        }
        x4(x34)//#hash_lookup
      }
      if (x33 != -1) {
        val x38 = x33 * 256
        val x39 = x38 + x12(x33)
        var x40 = x38
        while (x40 != x39) {
          val x42 = x11(x40)
          printf("%s,%s,%s,%s,%s,%s\n", x7(x42), x8(x42), x29, x30, x31, x32)
          x40 = x40 + 1
        }
      } else ()
    }
    x27.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
