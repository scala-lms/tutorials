/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((java.lang.String)=>(Unit)) {
  def apply(x0:java.lang.String): Unit = {
    val x1 = println("Name,Value,Flag,Name1")
    val x2 = new Array[Any](65536)
    val x3 = new Array[Any](65536)
    val x4 = new Array[Any](65536)
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
    val x30 = 0L.toInt
    val x31 = x30 & 255
    val x59 = while ({val x20 = x16.hasNext
      x20}) {
      val x22 = x16.next
      val x23 = x16.next
      val x24 = x16.next
      val x25 = x6
      val x26 = x2(x25) = x22
      val x27 = x3(x25) = x23
      val x28 = x4(x25) = x24
      val x29 = x6 += 1
      var x32: Int = x31
      val x33 = x32
      val x34 = x8(x33)
      var x35: Int = x34
      val x49 = while ({val x36 = x35
        val x37 = x36 != -1
        val x38 = x36 != x30
        val x39 = x37 && x38
        x39}) {
        val x41 = x32
        val x42 = x41 + 1
        val x43 = x42 & 255
        x32 = x43
        val x45 = x32
        val x46 = x8(x45)
        x35 = x46
        ()
      }
      val x50 = x32
      val x51 = x8(x50) = x30
      val x52 = x9(x50)
      val x53 = x50 * 256
      val x54 = x53 + x52
      val x55 = x7(x54) = x25
      val x56 = x52 + 1
      val x57 = x9(x50) = x56
      x57
    }
    val x60 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    val x61 = x60.next
    val x62 = x60.next
    val x63 = x60.next
    val x107 = while ({val x64 = x60.hasNext
      x64}) {
      val x66 = x60.next
      val x67 = x60.next
      val x68 = x60.next
      var x69: Int = x31
      val x70 = x69
      val x71 = x8(x70)
      var x72: Int = x71
      val x86 = while ({val x73 = x72
        val x74 = x73 != -1
        val x75 = x73 != x30
        val x76 = x74 && x75
        x76}) {
        val x78 = x69
        val x79 = x78 + 1
        val x80 = x79 & 255
        x69 = x80
        val x82 = x69
        val x83 = x8(x82)
        x72 = x83
        ()
      }
      val x87 = x69
      val x88 = x9(x87)
      val x89 = x87 * 256
      val x90 = x89 + x88
      var x92 : Int = x89
      val x105 = while (x92 < x90) {
        val x93 = x7(x92)
        val x94 = x2(x93)
        val x95 = x3(x93)
        val x96 = x4(x93)
        val x97 = x94+","
        val x98 = x95+","
        val x99 = x96+","
        val x100 = x99+x66
        val x101 = x98+x100
        val x102 = x97+x101
        val x103 = println(x102)
        x92 = x92 + 1
      }
      x105
    }
    x107
  }
}
/*****************************************
End of Generated Code
*******************************************/
