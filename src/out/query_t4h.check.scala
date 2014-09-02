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
    val x15 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    val x16 = x15.next(',')
    val x17 = x15.next(',')
    val x18 = x15.next('\n')
    val x29 = 0L.toInt
    val x30 = x29 & 255
    val x35 = !true
    val x54 = while ({val x19 = x15.hasNext
      x19}) {
      val x21 = x15.next(',')
      val x22 = x15.next(',')
      val x23 = x15.next('\n')
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
    val x55 = x15.close
    val x56 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    val x57 = x56.next(',')
    val x58 = x56.next(',')
    val x59 = x56.next('\n')
    val x93 = while ({val x60 = x56.hasNext
      x60}) {
      val x62 = x56.next(',')
      val x63 = x56.next(',')
      val x64 = x56.next('\n')
      val x79 = {
        //#hash_lookup
        // generated code for hash lookup
        var x65: Int = x30
        val x76 = while ({val x66 = x65
          val x67 = x9(x66)
          val x68 = x67 != 0
          val x69 = x68 && x35
          x69}) {
          val x71 = x65
          val x72 = x71 + 1
          val x73 = x72 & 255
          x65 = x73
          ()
        }
        val x77 = x65
        x77
        //#hash_lookup
      }
      val x80 = x9(x79)
      val x81 = x79 * 256
      val x82 = x81 + x80
      var x84 : Int = x81
      val x91 = while (x84 < x82) {
        val x85 = x7(x84)
        val x86 = x2(x85)
        val x87 = x3(x85)
        val x88 = x4(x85)
        val x89 = printf("%s,%s,%s,%s\n",x86,x87,x88,x62)
        x84 = x84 + 1
      }
      x91
    }
    val x94 = x56.close
    x94
  }
}
/*****************************************
End of Generated Code
*******************************************/
