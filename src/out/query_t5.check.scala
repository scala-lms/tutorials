/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Name,Value,Flag,Name")
    val x2 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x2.next(',')
    x2.next(',')
    x2.next('\n')
    while (x2.hasNext) {
      val x6 = x2.next(',')
      val x7 = x2.next(',')
      val x8 = x2.next('\n')
      val x9 = new scala.lms.tutorial.Scanner("src/data/t.csv")
      x9.next(',')
      x9.next(',')
      x9.next('\n')
      while (x9.hasNext) {
        val x13 = x9.next(',')
        x9.next(',')
        x9.next('\n')
        if (true && x6 == x13) {
          printf("%s,%s,%s,%s\n", x6, x7, x8, x13)
        }
      }
      x9.close
    }
    x2.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
