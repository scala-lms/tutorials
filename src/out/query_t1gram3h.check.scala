/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Word,Value,Phrase,Year,MatchCount,VolumeCount")
    var x1 = 0
    var x2 = 0
    val x3 = new Array[Int](256)
    var x4 = 0
    while ({
      x4 != 256
    }) {
      x3(x4) = -1
      x4 = x4 + 1
    }
    val x5 = new Array[java.lang.String](65536)
    val x6 = new Array[java.lang.String](65536)
    var x7 = 0
    var x8 = 0
    val x9 = new Array[Int](65536)
    val x10 = new Array[Int](256)
    val x11 = new scala.lms.tutorial.Scanner("src/data/words.csv")
    x11.next(',')
    x11.next('\n')
    val x12 = (0L.toInt) & 255
    val x13 = !true
    while ({
      x11.hasNext
    }) {
      val x14 = x8
      x5(x14) = x11.next(',')
      x6(x14) = x11.next('\n')
      x8 = x8 + 1
      val x15 = {
        //#hash_lookup
        // generated code for hash lookup
        var x16 = x12
        while ({
          ((x3(x16)) != -1) && {
            x13
          }
        }) {
          x16 = (x16 + 1) & 255
        }
        if ((x3(x16)) == -1) {
          val x17 = x2
          x2 = x2 + 1
          x3(x16) = x17
          x10(x17) = 0
          x17
        } else {
          x3(x16)
        }
        //#hash_lookup
      }
      val x18 = x10(x15)
      x9((x15 * 256) + x18) = x14
      x10(x15) = x18 + 1
    }
    x11.close
    val x19 = new scala.lms.tutorial.Scanner("src/data/t1gram.csv")
    while ({
      x19.hasNext
    }) {
      val x20 = x19.next('\t')
      val x21 = x19.next('\t')
      val x22 = x19.next('\t')
      val x23 = x19.next('\n')
      val x24 = {
        //#hash_lookup
        // generated code for hash lookup
        var x25 = x12
        while ({
          ((x3(x25)) != -1) && {
            x13
          }
        }) {
          x25 = (x25 + 1) & 255
        }
        x3(x25)
        //#hash_lookup
      }
      if (x24 != -1) {
        val x26 = x24 * 256
        val x27 = x26 + (x10(x24))
        var x28 = x26
        while ({
          x28 != x27
        }) {
          val x29 = x9(x28)
          printf("%s,%s,%s,%s,%s,%s\n", x5(x29), x6(x29), x20, x21, x22, x23)
          x28 = x28 + 1
        }
      } else {
      }
    }
    x19.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
