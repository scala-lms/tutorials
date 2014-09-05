/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((java.lang.String)=>(Unit)) {
  def apply(x0:java.lang.String): Unit = {
    val x1 = println("Phrase,Year,MatchCount,VolumeCount,Phrase")
    val x2 = new Array[java.lang.String](256)
    var x3: Int = 0
    var x4: Int = 0
    val x5 = new Array[Int](256)
    var x7 : Int = 0
    val x10 = while (x7 < 256) {
      val x8 = x5(x7) = -1
      x7 = x7 + 1
    }
    val x11 = new Array[java.lang.String](65536)
    val x12 = new Array[java.lang.String](65536)
    val x13 = new Array[java.lang.String](65536)
    val x14 = new Array[java.lang.String](65536)
    var x15: Int = 0
    var x16: Int = 0
    val x17 = new Array[Int](65536)
    val x18 = new Array[Int](256)
    val x19 = new scala.lms.tutorial.Scanner(x0)
    val x71 = while ({val x20 = x19.hasNext
      x20}) {
      val x22 = x19.next('\t')
      val x23 = x19.next('\t')
      val x24 = x19.next('\t')
      val x25 = x19.next('\n')
      val x26 = x16
      val x27 = x11(x26) = x22
      val x28 = x12(x26) = x23
      val x29 = x13(x26) = x24
      val x30 = x14(x26) = x25
      val x31 = x16 += 1
      val x32 = x22.##
      val x33 = x32.toInt
      val x34 = x33 & 255
      val x63 = {
        //#hash_lookup
        // generated code for hash lookup
        var x35: Int = x34
        val x51 = while ({val x36 = x35
          val x37 = x5(x36)
          val x39 = x37 == -1
          val x44 = if (x39) {
            false
          } else {
            val x40 = x2(x37)
            val x41 = x40 == x22
            val x42 = !x41
            x42
          }
          x44}) {
          val x46 = x35
          val x47 = x46 + 1
          val x48 = x47 & 255
          x35 = x48
          ()
        }
        val x52 = x35
        val x53 = x5(x52)
        val x54 = x53 == -1
        val x61 = if (x54) {
          val x55 = x4
          val x56 = x2(x55) = x22
          val x57 = x4 += 1
          val x58 = x5(x52) = x55
          val x59 = x18(x55) = 0
          x55
        } else {
          x53
        }
        x61
        //#hash_lookup
      }
      val x64 = x18(x63)
      val x65 = x63 * 256
      val x66 = x65 + x64
      val x67 = x17(x66) = x26
      val x68 = x64 + 1
      val x69 = x18(x63) = x68
      x69
    }
    val x72 = x19.close
    val x73 = new scala.lms.tutorial.Scanner("src/data/words.csv")
    val x74 = x73.next(',')
    val x75 = x73.next('\n')
    val x118 = while ({val x76 = x73.hasNext
      x76}) {
      val x78 = x73.next(',')
      val x79 = x73.next('\n')
      val x80 = x78.##
      val x81 = x80.toInt
      val x82 = x81 & 255
      val x103 = {
        //#hash_lookup
        // generated code for hash lookup
        var x83: Int = x82
        val x99 = while ({val x84 = x83
          val x85 = x5(x84)
          val x87 = x85 == -1
          val x92 = if (x87) {
            false
          } else {
            val x88 = x2(x85)
            val x89 = x88 == x78
            val x90 = !x89
            x90
          }
          x92}) {
          val x94 = x83
          val x95 = x94 + 1
          val x96 = x95 & 255
          x83 = x96
          ()
        }
        val x100 = x83
        val x101 = x5(x100)
        x101
        //#hash_lookup
      }
      val x104 = x18(x103)
      val x105 = x103 * 256
      val x106 = x105 + x104
      var x108 : Int = x105
      val x116 = while (x108 < x106) {
        val x109 = x17(x108)
        val x110 = x11(x109)
        val x111 = x12(x109)
        val x112 = x13(x109)
        val x113 = x14(x109)
        val x114 = printf("%s,%s,%s,%s,%s\n",x110,x111,x112,x113,x78)
        x108 = x108 + 1
      }
      x116
    }
    val x119 = x73.close
    ()
  }
}
/*****************************************
End of Generated Code
*******************************************/
