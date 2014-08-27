/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((java.lang.String)=>(Unit)) {
  def apply(x0:java.lang.String): Unit = {
    val x1 = println("Name,Value,Flag,Name")
    val x2 = new Array[java.lang.String](65536)
    val x3 = new Array[java.lang.String](65536)
    val x4 = new Array[java.lang.String](65536)
    var x5: Int = 0
    var x6: Int = 0
    val x7 = new Array[Int](65536)
    val x8 = new Array[Int](256)
    val x9 = new Array[Int](256)
    var x11 : Int = 0
    val x15 = while (x11 < 256) {
      val x12 = x8(x11) = -1
      val x13 = x9(x11) = 0
      x11 = x11 + 1
    }
    val x16 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    val x17 = x16.next
    val x18 = x16.next
    val x19 = x16.next
    val x60 = while ({val x20 = x16.hasNext
      x20}) {
      val x22 = x16.next
      val x23 = x16.next
      val x24 = x16.next
      val x25 = x6
      val x26 = x2(x25) = x22
      val x27 = x3(x25) = x23
      val x28 = x4(x25) = x24
      val x29 = x6 += 1
      val x30 = x22.##
      val x31 = x30.toInt
      val x32 = x31 & 255
      var x33: Int = x32
      val x34 = x33
      val x35 = x8(x34)
      var x36: Int = x35
      val x50 = while ({val x37 = x36
        val x38 = x37 != -1
        val x39 = x37 != x31
        val x40 = x38 && x39
        x40}) {
        val x42 = x33
        val x43 = x42 + 1
        val x44 = x43 & 255
        x33 = x44
        val x46 = x33
        val x47 = x8(x46)
        x36 = x47
        ()
      }
      val x51 = x33
      val x52 = x8(x51) = x31
      val x53 = x9(x51)
      val x54 = x51 * 256
      val x55 = x54 + x53
      val x56 = x7(x55) = x25
      val x57 = x53 + 1
      val x58 = x9(x51) = x57
      x58
    }
    val x61 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    val x62 = x61.next
    val x63 = x61.next
    val x64 = x61.next
    val x111 = while ({val x65 = x61.hasNext
      x65}) {
      val x67 = x61.next
      val x68 = x61.next
      val x69 = x61.next
      val x70 = x67.##
      val x71 = x70.toInt
      val x72 = x71 & 255
      var x73: Int = x72
      val x74 = x73
      val x75 = x8(x74)
      var x76: Int = x75
      val x90 = while ({val x77 = x76
        val x78 = x77 != -1
        val x79 = x77 != x71
        val x80 = x78 && x79
        x80}) {
        val x82 = x73
        val x83 = x82 + 1
        val x84 = x83 & 255
        x73 = x84
        val x86 = x73
        val x87 = x8(x86)
        x76 = x87
        ()
      }
      val x91 = x73
      val x92 = x9(x91)
      val x93 = x91 * 256
      val x94 = x93 + x92
      var x96 : Int = x93
      val x109 = while (x96 < x94) {
        val x97 = x7(x96)
        val x98 = x2(x97)
        val x99 = x3(x97)
        val x100 = x4(x97)
        val x101 = x98+","
        val x102 = x99+","
        val x103 = x100+","
        val x104 = x103+x67
        val x105 = x102+x104
        val x106 = x101+x105
        val x107 = println(x106)
        x96 = x96 + 1
      }
      x109
    }
    x111
  }
}
/*****************************************
End of Generated Code
*******************************************/
