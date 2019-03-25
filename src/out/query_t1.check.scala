/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Name,Value,Flag")
    val x1 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x1.next(',')
    x1.next(',')
    x1.next('\n')
    while ({
      x1.hasNext
    }) {
      printf("%s,%s,%s\n", x1.next(','), x1.next(','), x1.next('\n'))
    }
    x1.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
