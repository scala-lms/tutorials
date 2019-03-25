/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Name,Value")
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
    val x6 = new Array[Int](256)
    var x7 = 0
    val x8 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x8.next(',')
    x8.next(',')
    x8.next('\n')
    while ({
      x8.hasNext
    }) {
      val x9 = x8.next(',')
      val x10 = x8.next(',')
      x8.next('\n')
      val x11 = ((x9.hashCode).toInt) & 255
      val x12 = {
        //#hash_lookup
        // generated code for hash lookup
        var x13 = x11
        while ({
          ((x4(x13)) != -1) && {
            !((x1(x4(x13))) == x9)
          }
        }) {
          x13 = (x13 + 1) & 255
        }
        if ((x4(x13)) == -1) {
          val x14 = x3
          x1(x14) = x9
          x3 = x3 + 1
          x4(x13) = x14
          x6(x14) = 0
          x14
        } else {
          x4(x13)
        }
        //#hash_lookup
      }
      x6(x12) = (x6(x12)) + (x10.toInt)
    }
    x8.close
    val x15 = x3
    var x16 = 0
    while ({
      x16 != x15
    }) {
      val x17 = x16
      printf("%s,%s\n", x1(x17), (x6(x17)).toString)
      x16 = x16 + 1
    }
  }
}
/*****************************************
End of Generated Code
*******************************************/
