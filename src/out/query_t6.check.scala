/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Name,Value")
    val x1 = new Array[java.lang.String](256)
    var x2 = 0
    val x3 = new Array[Int](256)
    var x4 = 0
    while (x4 != 256) {
      x3(x4) = -1
      x4 = x4 + 1
    }
    val x5 = new Array[Int](256)
    val x6 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x6.next(',')
    x6.next(',')
    x6.next('\n')
    while (x6.hasNext) {
      val x7 = x6.next(',')
      val x8 = x6.next(',')
      x6.next('\n')
      val x9 = x7.hashCode.toInt & 255
      //# hash_lookup
      // generated code for hash lookup
      val x10 = {
        var x11 = x9
        while (x3(x11) != -1 && !(x1(x3(x11)) == x7)) {
          x11 = x11 + 1 & 255
        }
        if (x3(x11) == -1) {
          val x12 = x2
          x1(x12) = x7
          x2 = x2 + 1
          x3(x11) = x12
          x5(x12) = 0
          x12
        } else x3(x11)
      }
      //# hash_lookup
      x5(x10) = x5(x10) + x8.toInt
    }
    x6.close
    val x13 = x2
    var x14 = 0
    while (x14 != x13) {
      val x15 = x14
      printf("%s,%s\n", x1(x15), x5(x15).toString)
      x14 = x14 + 1
    }
  }
}
/*****************************************
End of Generated Code
*******************************************/
