/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Name,Value")
    val x1 = new Array[java.lang.String](256)
    var x2 = 0
    var x3 = 0
    val x4 = new Array[Int](256)
    var x5 = 0
    while (x5 != 256) {
      x4(x5) = -1
      x5 = x5 + 1
    }
    val x6 = new Array[Int](256)
    var x7 = 0
    val x8 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x8.next(',')
    x8.next(',')
    x8.next('\n')
    val x9 = 0L * 41L
    while (x8.hasNext) {
      val x10 = x8.next(',')
      val x11 = x8.next(',')
      x8.next('\n')
      val x12 = ((x9 + x10.hashCode).toInt) & 255
      val x13 = {
        //#hash_lookup
        // generated code for hash lookup
        var x14 = x12
        while (x4(x14) != -1 && !(x1(x4(x14)) == x10)) x14 = (x14 + 1) & 255
        if (x4(x14) == -1) {
          val x15 = x3
          x1(x15) = x10
          x3 = x3 + 1
          x4(x14) = x15
          x6(x15) = 0
          x15
        } else x4(x14)
        //#hash_lookup
      }
      x6(x13) = x6(x13) + x11.toInt
    }
    x8.close
    val x16 = x3
    var x17 = 0
    while (x17 != x16) {
      val x18 = x17
      printf("%s,%s\n", x1(x18), (x6(x18)).toString)
      x17 = x17 + 1
    }
  }
}
/*****************************************
End of Generated Code
*******************************************/
