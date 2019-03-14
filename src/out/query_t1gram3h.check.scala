/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Word,Value,Phrase,Year,MatchCount,VolumeCount")
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
      val x18 = x10
      x7(x18) = x13.next(',')
      x8(x18) = x13.next('\n')
      x10 = x10 + 1
      val x23 = {
        //#hash_lookup
        // generated code for hash lookup
        var x19 = x16
        while ({
          x4(x19) != -1 && {
            x4(x19)
            x17
          }
        }) {
          x19 = (x19 + 1) & 255
        }
        if (x4(x19) == -1) {
          val x22 = x3
          x3 = x3 + 1
          x4(x19) = x22
          x12(x22) = 0
          x22
        } else x4(x19)//#hash_lookup
      }
      val x24 = x12(x23)
      x11(x23 * 256 + x24) = x18
      x12(x23) = x24 + 1
    }
    x13.close
    val x27 = new scala.lms.tutorial.Scanner(x0)
    while (x27.hasNext) {
      val x28 = x27.next('\t')
      val x29 = x27.next('\t')
      val x30 = x27.next('\t')
      val x31 = x27.next('\n')
      val x35 = {
        //#hash_lookup
        // generated code for hash lookup
        var x32 = x16
        while ({
          x4(x32) != -1 && {
            x4(x32)
            x17
          }
        }) {
          x32 = (x32 + 1) & 255
        }
        x4(x32)//#hash_lookup
      }
      if (x35 != -1) {
        val x36 = x35 * 256
        val x37 = x36 + x12(x35)
        var x38 = x36
        while (x38 != x37) {
          val x39 = x11(x38)
          printf("%s,%s,%s,%s,%s,%s\n", x7(x39), x8(x39), x28, x29, x30, x31)
          x38 = x38 + 1
        }
      }
    }
    x27.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
