/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Name")
    val x1 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x1.next(',')
    x1.next(',')
    x1.next('\n')
    while (x1.hasNext) {
      val x2 = x1.next(',')
      x1.next(',')
      if (x1.next('\n') == "yes") printf("%s\n", x2) else {
      }
    }
    x1.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
