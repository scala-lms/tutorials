/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Name,Value,Flag")
    val x2 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    val x3 = x2.next(',')
    val x4 = x2.next(',')
    val x5 = x2.next('\n')
    val x7 = while (x2.hasNext) {
      val x6 = printf("%s,%s,%s\n", x2.next(','), x2.next(','), x2.next('\n'))
    }
    val x8 = x2.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
