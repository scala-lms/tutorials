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
    val x8 = new Array[java.lang.String](256)
    var x9: Int = 0
    val x10 = new Array[Int](256)
    var x12 : Int = 0
    val x15 = while (x12 < 256) {
      val x13 = x10(x12) = 0
      x12 = x12 + 1
    }
    val x16 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    val x17 = x16.next(',')
    val x18 = x16.next(',')
    val x19 = x16.next('\n')
    val x60 = while ({val x20 = x16.hasNext
      x20}) {
      val x22 = x16.next(',')
      val x23 = x16.next(',')
      val x24 = x16.next('\n')
      val x25 = x6
      val x26 = x2(x25) = x22
      val x27 = x3(x25) = x23
      val x28 = x4(x25) = x24
      val x29 = x6 += 1
      val x30 = x22.##
      val x31 = x30.toInt
      val x32 = x31 & 255
      val x52 = {
        //#hash_lookup
        // generated code for hash lookup
        var x33: Int = x32
        val x48 = while ({val x34 = x33
          val x35 = x10(x34)
          val x37 = x8(x34)
          val x36 = x35 != 0
          val x38 = x37 == x22
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
    val x61 = x16.close
    val x62 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    val x63 = x62.next(',')
    val x64 = x62.next(',')
    val x65 = x62.next('\n')
    val x106 = while ({val x66 = x62.hasNext
      x66}) {
      val x68 = x62.next(',')
      val x69 = x62.next(',')
      val x70 = x62.next('\n')
      val x71 = x68.##
      val x72 = x71.toInt
      val x73 = x72 & 255
      val x92 = {
        //#hash_lookup
        // generated code for hash lookup
        var x74: Int = x73
        val x89 = while ({val x75 = x74
          val x76 = x10(x75)
          val x78 = x8(x75)
          val x77 = x76 != 0
          val x79 = x78 == x68
          val x80 = true && x79
          val x81 = !x80
          val x82 = x77 && x81
          x82}) {
          val x84 = x74
          val x85 = x84 + 1
          val x86 = x85 & 255
          x74 = x86
          ()
        }
        val x90 = x74
        x90
        //#hash_lookup
      }
      val x93 = x10(x92)
      val x94 = x92 * 256
      val x95 = x94 + x93
      var x97 : Int = x94
      val x104 = while (x97 < x95) {
        val x98 = x7(x97)
        val x99 = x2(x98)
        val x100 = x3(x98)
        val x101 = x4(x98)
        val x102 = printf("%s,%s,%s,%s\n",x99,x100,x101,x68)
        x97 = x97 + 1
      }
      x104
    }
    val x107 = x62.close
    x107
  }
}
/*****************************************
End of Generated Code
*******************************************/
