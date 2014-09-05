/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((java.lang.String)=>(Unit)) {
  def apply(x0:java.lang.String): Unit = {
    val x1 = println("Phrase,Year,MatchCount,VolumeCount,Word,Value")
    var x2: Int = 0
    var x3: Int = 0
    val x4 = new Array[Int](256)
    var x6 : Int = 0
    val x9 = while (x6 < 256) {
      val x7 = x4(x6) = -1
      x6 = x6 + 1
    }
    val x10 = new Array[java.lang.String](65536)
    val x11 = new Array[java.lang.String](65536)
    val x12 = new Array[java.lang.String](65536)
    val x13 = new Array[java.lang.String](65536)
    var x14: Int = 0
    var x15: Int = 0
    val x16 = new Array[Int](65536)
    val x17 = new Array[Int](256)
    val x18 = new scala.lms.tutorial.Scanner(x0)
    val x31 = 0L.toInt
    val x32 = x31 & 255
    val x38 = !true
    val x65 = while ({val x19 = x18.hasNext
      x19}) {
      val x21 = x18.next('\t')
      val x22 = x18.next('\t')
      val x23 = x18.next('\t')
      val x24 = x18.next('\n')
      val x25 = x15
      val x26 = x10(x25) = x21
      val x27 = x11(x25) = x22
      val x28 = x12(x25) = x23
      val x29 = x13(x25) = x24
      val x30 = x15 += 1
      val x57 = {
        //#hash_lookup
        // generated code for hash lookup
        var x33: Int = x32
        val x46 = while ({val x34 = x33
          val x35 = x4(x34)
          val x37 = x35 == -1
          val x39 = if (x37) {
            false
          } else {
            x38
          }
          x39}) {
          val x41 = x33
          val x42 = x41 + 1
          val x43 = x42 & 255
          x33 = x43
          ()
        }
        val x47 = x33
        val x48 = x4(x47)
        val x49 = x48 == -1
        val x55 = if (x49) {
          val x50 = x3
          val x51 = x3 += 1
          val x52 = x4(x47) = x50
          val x53 = x17(x50) = 0
          x50
        } else {
          x48
        }
        x55
        //#hash_lookup
      }
      val x58 = x17(x57)
      val x59 = x57 * 256
      val x60 = x59 + x58
      val x61 = x16(x60) = x25
      val x62 = x58 + 1
      val x63 = x17(x57) = x62
      x63
    }
    val x66 = x18.close
    val x67 = new scala.lms.tutorial.Scanner("src/data/words.csv")
    val x68 = x67.next(',')
    val x69 = x67.next('\n')
    val x108 = while ({val x70 = x67.hasNext
      x70}) {
      val x72 = x67.next(',')
      val x73 = x67.next('\n')
      val x90 = {
        //#hash_lookup
        // generated code for hash lookup
        var x74: Int = x32
        val x86 = while ({val x75 = x74
          val x76 = x4(x75)
          val x78 = x76 == -1
          val x79 = if (x78) {
            false
          } else {
            x38
          }
          x79}) {
          val x81 = x74
          val x82 = x81 + 1
          val x83 = x82 & 255
          x74 = x83
          ()
        }
        val x87 = x74
        val x88 = x4(x87)
        x88
        //#hash_lookup
      }
      val x91 = x17(x90)
      val x92 = x90 * 256
      val x93 = x92 + x91
      var x95 : Int = x92
      val x106 = while (x95 < x93) {
        val x96 = x16(x95)
        val x97 = x10(x96)
        val x98 = x11(x96)
        val x99 = x12(x96)
        val x100 = x13(x96)
        val x101 = x72 == x97
        val x104 = if (x101) {
          val x102 = printf("%s,%s,%s,%s,%s,%s\n",x97,x98,x99,x100,x72,x73)
          x102
        } else {
          ()
        }
        x95 = x95 + 1
      }
      x106
    }
    val x109 = x67.close
    ()
  }
}
/*****************************************
End of Generated Code
*******************************************/
