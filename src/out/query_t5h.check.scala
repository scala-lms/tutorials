/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Name,Value,Flag,Name")
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
    val x8 = new Array[java.lang.String](65536)
    var x9 = 0
    var x10 = 0
    val x11 = new Array[Int](65536)
    val x12 = new Array[Int](256)
    val x13 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x13.next(',')
    x13.next(',')
    x13.next('\n')
    while ({
      x13.hasNext
    }) {
      val x14 = x13.next(',')
      val x15 = x10
      x6(x15) = x14
      x7(x15) = x13.next(',')
      x8(x15) = x13.next('\n')
      x10 = x10 + 1
      val x16 = ((x14.hashCode).toInt) & 255
      //# hash_lookup
      // generated code for hash lookup
      val x17 = {
        var x18 = x16
        while ({
          ((x4(x18)) != -1) && {
            !((x1(x4(x18))) == x14)
          }
        }) {
          x18 = (x18 + 1) & 255
        }
        if ((x4(x18)) == -1) {
          val x19 = x3
          x1(x19) = x14
          x3 = x3 + 1
          x4(x18) = x19
          x12(x19) = 0
          x19
        } else {
          x4(x18)
        }
      }
      //# hash_lookup
      val x20 = x12(x17)
      x11((x17 * 256) + x20) = x15
      x12(x17) = x20 + 1
    }
    x13.close
    val x21 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x21.next(',')
    x21.next(',')
    x21.next('\n')
    while ({
      x21.hasNext
    }) {
      val x22 = x21.next(',')
      x21.next(',')
      x21.next('\n')
      val x23 = ((x22.hashCode).toInt) & 255
      //# hash_lookup
      // generated code for hash lookup
      val x24 = {
        var x25 = x23
        while ({
          ((x4(x25)) != -1) && {
            !((x1(x4(x25))) == x22)
          }
        }) {
          x25 = (x25 + 1) & 255
        }
        x4(x25)
      }
      //# hash_lookup
      if (x24 != -1) {
        val x26 = x24 * 256
        val x27 = x26 + (x12(x24))
        var x28 = x26
        while ({
          x28 != x27
        }) {
          val x29 = x11(x28)
          printf("%s,%s,%s,%s\n", x6(x29), x7(x29), x8(x29), x22)
          x28 = x28 + 1
        }
      } else {
      }
    }
    x21.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
