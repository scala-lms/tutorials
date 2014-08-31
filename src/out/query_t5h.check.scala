/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((java.lang.String)=>(Unit)) {
  def apply(x0:java.lang.String): Unit = {
    val x1 = println("Name,Value,Flag,Name")
    val x2 = new Array[Any](65536)
    val x3 = new Array[Any](65536)
    val x4 = new Array[Any](65536)
    var x5: Int = 0
    var x6: Int = 0
    val x7 = new Array[Int](65536)
    val x8 = new Array[Any](256)
    var x9: Int = 0
    val x10 = new Array[Int](256)
    var x12 : Int = 0
    val x15 = while (x12 < 256) {
      val x13 = x10(x12) = 0
      x12 = x12 + 1
    }
    val x16 = new scala.lms.tutorial.Scanner("src/data/t.csv", ',')
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
      val x52 = {
        //#hash_lookup
        // generated code for hash lookup
        val x30 = x22.##
        val x31 = x30.toInt
        val x32 = x31 & 255
        var x33: Int = x32
        val x48 = while ({val x34 = x33
          val x35 = x10(x34)
          val x37 = x8(x34)
          val x38 = x37 == x22
          val x36 = x35 != 0
          val x39 = true && x38
          val x40 = !x39
          val x41 = x36 && x40
          x41}) {
          val x43 = x33
          val x44 = x43 + 1
          val x45 = x44 & 255
          x33 = x45
          ()
        }
        val x49 = x33
        val x50 = x8(x49) = x22
        x49
        //#hash_lookup
      }
      val x53 = x10(x52)
      val x54 = x52 * 256
      val x55 = x54 + x53
      val x56 = x7(x55) = x25
      val x57 = x53 + 1
      val x58 = x10(x52) = x57
      x58
    }
    val x61 = new scala.lms.tutorial.Scanner("src/data/t.csv", ',')
    val x62 = x61.next
    val x63 = x61.next
    val x64 = x61.next
    val x105 = while ({val x65 = x61.hasNext
      x65}) {
      val x67 = x61.next
      val x68 = x61.next
      val x69 = x61.next
      val x91 = {
        //#hash_lookup
        // generated code for hash lookup
        val x70 = x67.##
        val x71 = x70.toInt
        val x72 = x71 & 255
        var x73: Int = x72
        val x88 = while ({val x74 = x73
          val x75 = x10(x74)
          val x77 = x8(x74)
          val x78 = x77 == x67
          val x76 = x75 != 0
          val x79 = true && x78
          val x80 = !x79
          val x81 = x76 && x80
          x81}) {
          val x83 = x73
          val x84 = x83 + 1
          val x85 = x84 & 255
          x73 = x85
          ()
        }
        val x89 = x73
        x89
        //#hash_lookup
      }
      val x92 = x10(x91)
      val x93 = x91 * 256
      val x94 = x93 + x92
      var x96 : Int = x93
      val x103 = while (x96 < x94) {
        val x97 = x7(x96)
        val x98 = x2(x97)
        val x99 = x3(x97)
        val x100 = x4(x97)
        val x101 = printf("%s,%s,%s,%s\n",x98,x99,x100,x67)
        x96 = x96 + 1
      }
      x103
    }
    x105
  }
}
/*****************************************
End of Generated Code
*******************************************/
