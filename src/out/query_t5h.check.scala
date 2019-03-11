/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Name,Value,Flag,Name")
    val x2 = new Array[java.lang.String](256)
    var x3 = 0
    var x4 = 0
    val x5 = new Array[Int](256)
    var x6 = 0
    while (x6 != 256) {
      x5(x6) = -1
      x6 = x6 + 1
    }
    val x8 = new Array[java.lang.String](65536)
    val x9 = new Array[java.lang.String](65536)
    val x10 = new Array[java.lang.String](65536)
    var x11 = 0
    var x12 = 0
    val x13 = new Array[Int](65536)
    val x14 = new Array[Int](256)
    val x15 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x15.next(',')
    x15.next(',')
    x15.next('\n')
    val x19 = 0L * 41L
    while (x15.hasNext) {
      val x21 = x15.next(',')
      val x22 = x12
      x8(x22) = x21
      x9(x22) = x15.next(',')
      x10(x22) = x15.next('\n')
      x12 = x12 + 1
      val x23 = ((x19 + x21.hashCode).toInt) & 255
      val x24 = {
        //#hash_lookup
        // generated code for hash lookup
        var x25 = x23
        while ({
          x5(x25) != -1 && {
            val x27 = x2(x5(x25))
            !(true && x27 == x21)
          }
        }) {
          x25 = (x25 + 1) & 255
        }
        if (x5(x25) == -1) {
          val x28 = x4
          x2(x28) = x21
          x4 = x4 + 1
          x5(x25) = x28
          x14(x28) = 0
          x28
        } else x5(x25)//#hash_lookup
      }
      val x29 = x14(x24)
      x13(x24 * 256 + x29) = x22
      x14(x24) = x29 + 1
    }
    x15.close
    val x31 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x31.next(',')
    x31.next(',')
    x31.next('\n')
    while (x31.hasNext) {
      val x36 = x31.next(',')
      x31.next(',')
      x31.next('\n')
      val x39 = ((x19 + x36.hashCode).toInt) & 255
      val x40 = {
        //#hash_lookup
        // generated code for hash lookup
        var x41 = x39
        while ({
          x5(x41) != -1 && {
            val x43 = x2(x5(x41))
            !(true && x43 == x36)
          }
        }) {
          x41 = (x41 + 1) & 255
        }
        x5(x41)//#hash_lookup
      }
      if (x40 != -1) {
        val x45 = x40 * 256
        val x46 = x45 + x14(x40)
        var x47 = x45
        while (x47 != x46) {
          val x49 = x13(x47)
          printf("%s,%s,%s,%s\n", x8(x49), x9(x49), x10(x49), x36)
          x47 = x47 + 1
        }
      } else ()
    }
    x31.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
