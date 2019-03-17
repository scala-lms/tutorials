/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Name,Value,Flag,Name")
    val x1 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x1.next(',')
    x1.next(',')
    x1.next('\n')
    while (x1.hasNext) {
      val x2 = x1.next(',')
      val x3 = x1.next(',')
      val x4 = x1.next('\n')
      val x5 = new scala.lms.tutorial.Scanner("src/data/t.csv")
      x5.next(',')
      x5.next(',')
      x5.next('\n')
      while (x5.hasNext) {
        val x6 = x5.next(',')
        x5.next(',')
        x5.next('\n')
        if (x2 == x6) printf("%s,%s,%s,%s\n", x2, x3, x4, x6) else {
        }
      }
      x5.close
    }
    x1.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
