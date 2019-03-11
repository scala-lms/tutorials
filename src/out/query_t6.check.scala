/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Name,Value")
    val x2 = new Array[java.lang.String](256)
    var x3 = 0
    var x4 = 0
    val x5 = new Array[Int](256)
    var x6 = 0
    while (x6 != 256) {
      x5(x6) = -1
      x6 = x6 + 1
    }
    val x8 = new Array[Int](256)
    var x9 = 0
    val x10 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x10.next(',')
    x10.next(',')
    x10.next('\n')
    val x14 = 0L * 41L
    while (x10.hasNext) {
      val x16 = x10.next(',')
      val x17 = x10.next(',')
      x10.next('\n')
      val x19 = ((x14 + x16.hashCode).toInt) & 255
      val x20 = {
        //#hash_lookup
        // generated code for hash lookup
        var x21 = x19
        while ({
          x5(x21) != -1 && {
            val x23 = x2(x5(x21))
            !(true && x23 == x16)
          }
        }) {
          x21 = (x21 + 1) & 255
        }
        if (x5(x21) == -1) {
          val x24 = x4
          x2(x24) = x16
          x4 = x4 + 1
          x5(x21) = x24
          x8(x24) = 0
          x24
        } else x5(x21)//#hash_lookup
      }
      x8(x20) = x8(x20) + x17.toInt
    }
    x10.close
    val x26 = x4
    var x27 = 0
    while (x27 != x26) {
      val x29 = x27
      printf("%s,%s\n", x2(x29), (x8(x29)).toString)
      x27 = x27 + 1
    }
  }
}
/*****************************************
End of Generated Code
*******************************************/
