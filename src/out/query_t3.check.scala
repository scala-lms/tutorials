/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Name")
    val x2 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x2.next(',')
    x2.next(',')
    x2.next('\n')
    while (x2.hasNext) {
      val x6 = x2.next(',')
      x2.next(',')
      if (x2.next('\n') == "yes") {
        printf("%s\n", x6)
      }
    }
    x2.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
