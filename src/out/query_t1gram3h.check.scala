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
    val x12 = 0 & 255
    while ({
      x11.hasNext
    }) {
      val x13 = x8
      x5(x13) = x11.next(',')
      x6(x13) = x11.next('\n')
      x8 = x8 + 1
      //# hash_lookup
      // generated code for hash lookup
      val x14 = {
        var x15 = x12
        while ({
          ((x3(x15)) != -1) && {
            false
          }
        }) {
          x15 = (x15 + 1) & 255
        }
        if ((x3(x15)) == -1) {
          val x16 = x2
          x2 = x2 + 1
          x3(x15) = x16
          x10(x16) = 0
          x16
        } else {
          x3(x15)
        }
      }
      //# hash_lookup
      val x17 = x10(x14)
      x9((x14 * 256) + x17) = x13
      x10(x14) = x17 + 1
    }
    x11.close
    val x18 = new scala.lms.tutorial.Scanner("src/data/t1gram.csv")
    while ({
      x18.hasNext
    }) {
      val x19 = x18.next('\t')
      val x20 = x18.next('\t')
      val x21 = x18.next('\t')
      val x22 = x18.next('\n')
      //# hash_lookup
      // generated code for hash lookup
      val x23 = {
        var x24 = x12
        while ({
          ((x3(x24)) != -1) && {
            false
          }
        }) {
          x24 = (x24 + 1) & 255
        }
        x3(x24)
      }
      //# hash_lookup
      if (x23 != -1) {
        val x25 = x23 * 256
        val x26 = x25 + (x10(x23))
        var x27 = x25
        while ({
          x27 != x26
        }) {
          val x28 = x9(x27)
          printf("%s,%s,%s,%s,%s,%s\n", x5(x28), x6(x28), x19, x20, x21, x22)
          x27 = x27 + 1
        }
      } else {
      }
    }
    x18.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
