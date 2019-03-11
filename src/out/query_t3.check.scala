/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Name")
    val x2 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x2.next(',')
    x2.next(',')
    x2.next('\n')
    while (x2.hasNext) {
      val x7 = x2.next(',')
      x2.next(',')
      if (x2.next('\n') == "yes") {
        printf("%s\n", x7)
      } else ()
    }
    x2.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
