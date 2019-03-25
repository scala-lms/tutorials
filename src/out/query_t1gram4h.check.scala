/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Word,Value,Word,Year,MatchCount,VolumeCount")
    val x1 = new Array[java.lang.String](256)
    var x2 = 0
    var x3 = 0
    val x4 = new Array[Int](256)
    var x5 = 0
    while ({
      x5 != 256
    }) {
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
    while ({
      x12.hasNext
    }) {
      val x13 = x12.next(',')
      val x14 = x9
      x6(x14) = x13
      x7(x14) = x12.next('\n')
      x9 = x9 + 1
      val x15 = ((x13.hashCode).toInt) & 255
      val x16 = {
        //#hash_lookup
        // generated code for hash lookup
        var x17 = x15
        while ({
          ((x4(x17)) != -1) && {
            !((x1(x4(x17))) == x13)
          }
        }) {
          x17 = (x17 + 1) & 255
        }
        if ((x4(x17)) == -1) {
          val x18 = x3
          x1(x18) = x13
          x3 = x3 + 1
          x4(x17) = x18
          x11(x18) = 0
          x18
        } else {
          x4(x17)
        }
        //#hash_lookup
      }
      val x19 = x11(x16)
      x10((x16 * 256) + x19) = x14
      x11(x16) = x19 + 1
    }
    x12.close
    val x20 = new scala.lms.tutorial.Scanner("src/data/t1gram.csv")
    while ({
      x20.hasNext
    }) {
      val x21 = x20.next('\t')
      val x22 = x20.next('\t')
      val x23 = x20.next('\t')
      val x24 = x20.next('\n')
      val x25 = ((x21.hashCode).toInt) & 255
      val x26 = {
        //#hash_lookup
        // generated code for hash lookup
        var x27 = x25
        while ({
          ((x4(x27)) != -1) && {
            !((x1(x4(x27))) == x21)
          }
        }) {
          x27 = (x27 + 1) & 255
        }
        x4(x27)
        //#hash_lookup
      }
      if (x26 != -1) {
        val x28 = x26 * 256
        val x29 = x28 + (x11(x26))
        var x30 = x28
        while ({
          x30 != x29
        }) {
          val x31 = x10(x30)
          printf("%s,%s,%s,%s,%s,%s\n", x6(x31), x7(x31), x21, x22, x23, x24)
          x30 = x30 + 1
        }
      } else {
      }
    }
    x20.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
