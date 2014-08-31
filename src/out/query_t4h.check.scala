/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((java.lang.String)=>(Unit)) {
  def apply(x0:java.lang.String): Unit = {
    val x1 = println("Name,Value,Flag,Name1")
    val x2 = new Array[java.lang.String](65536)
    val x3 = new Array[java.lang.String](65536)
    val x4 = new Array[java.lang.String](65536)
    var x5: Int = 0
    var x6: Int = 0
    val x7 = new Array[Int](65536)
    var x8: Int = 0
    val x9 = new Array[Int](256)
    var x11 : Int = 0
    val x14 = while (x11 < 256) {
      val x12 = x9(x11) = 0
      x11 = x11 + 1
    }
    val x15 = new scala.lms.tutorial.Scanner("src/data/t.csv", ',')
    val x16 = x15.next
    val x17 = x15.next
    val x18 = x15.next
    val x29 = 0L.toInt
    val x30 = x29 & 255
    val x35 = !true
    val x54 = while ({val x19 = x15.hasNext
      x19}) {
      val x21 = x15.next
      val x22 = x15.next
      val x23 = x15.next
      val x24 = x6
      val x25 = x2(x24) = x21
      val x26 = x3(x24) = x22
      val x27 = x4(x24) = x23
      val x28 = x6 += 1
      val x46 = {
        //#hash_lookup
        // generated code for hash lookup
        var x31: Int = x30
        val x43 = while ({val x32 = x31
          val x33 = x9(x32)
          val x34 = x33 != 0
          val x36 = x34 && x35
          x36}) {
          val x38 = x31
          val x39 = x38 + 1
          val x40 = x39 & 255
          x31 = x40
          ()
        }
        val x44 = x31
        x44
        //#hash_lookup
      }
      val x47 = x9(x46)
      val x48 = x46 * 256
      val x49 = x48 + x47
      val x50 = x7(x49) = x24
      val x51 = x47 + 1
      val x52 = x9(x46) = x51
      x52
    }
    val x55 = new scala.lms.tutorial.Scanner("src/data/t.csv", ',')
    val x56 = x55.next
    val x57 = x55.next
    val x58 = x55.next
    val x92 = while ({val x59 = x55.hasNext
      x59}) {
      val x61 = x55.next
      val x62 = x55.next
      val x63 = x55.next
      val x78 = {
        //#hash_lookup
        // generated code for hash lookup
        var x64: Int = x30
        val x75 = while ({val x65 = x64
          val x66 = x9(x65)
          val x67 = x66 != 0
          val x68 = x67 && x35
          x68}) {
          val x70 = x64
          val x71 = x70 + 1
          val x72 = x71 & 255
          x64 = x72
          ()
        }
        val x76 = x64
        x76
        //#hash_lookup
      }
      val x79 = x9(x78)
      val x80 = x78 * 256
      val x81 = x80 + x79
      var x83 : Int = x80
      val x90 = while (x83 < x81) {
        val x84 = x7(x83)
        val x85 = x2(x84)
        val x86 = x3(x84)
        val x87 = x4(x84)
        val x88 = printf("%s,%s,%s,%s\n",x85,x86,x87,x61)
        x83 = x83 + 1
      }
      x90
    }
    x92
  }
}
/*****************************************
End of Generated Code
*******************************************/
