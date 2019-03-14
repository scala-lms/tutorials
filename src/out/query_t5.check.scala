/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Name,Value,Flag,Name")
    val x2 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    val x3 = x2.next(',')
    val x4 = x2.next(',')
    val x5 = x2.next('\n')
    val x20 = while (x2.hasNext) {
      val x6 = x2.next(',')
      val x7 = x2.next(',')
      val x8 = x2.next('\n')
      val x9 = new scala.lms.tutorial.Scanner("src/data/t.csv")
      val x10 = x9.next(',')
      val x11 = x9.next(',')
      val x12 = x9.next('\n')
      val x18 = while (x9.hasNext) {
        val x13 = x9.next(',')
        val x14 = x9.next(',')
        val x15 = x9.next('\n')
        val x17 = if (true && x6 == x13) {
          val x16 = printf("%s,%s,%s,%s\n", x6, x7, x8, x13)
        }
      }
      val x19 = x9.close
    }
    val x21 = x2.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
