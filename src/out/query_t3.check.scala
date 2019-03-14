/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Name")
    val x2 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    val x3 = x2.next(',')
    val x4 = x2.next(',')
    val x5 = x2.next('\n')
    val x10 = while (x2.hasNext) {
      val x6 = x2.next(',')
      val x7 = x2.next(',')
      val x9 = if (x2.next('\n') == "yes") {
        val x8 = printf("%s\n", x6)
      }
    }
    val x11 = x2.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
