/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Name,Value,Flag")
    val x2 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x2.next(',')
    x2.next(',')
    x2.next('\n')
    while (x2.hasNext) {
      printf("%s,%s,%s\n", x2.next(','), x2.next(','), x2.next('\n'))
    }
    x2.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
