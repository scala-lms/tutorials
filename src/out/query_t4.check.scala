/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Name,Value,Flag,Name1")
    val x2 = new scala.lms.tutorial.Scanner("src/data/t.csv")
    x2.next(',')
    x2.next(',')
    x2.next('\n')
    while (x2.hasNext) {
      val x7 = x2.next(',')
      val x8 = x2.next(',')
      val x9 = x2.next('\n')
      val x10 = new scala.lms.tutorial.Scanner("src/data/t.csv")
      x10.next(',')
      x10.next(',')
      x10.next('\n')
      while (x10.hasNext) {
        val x15 = x10.next(',')
        x10.next(',')
        x10.next('\n')
        if (true) {
          printf("%s,%s,%s,%s\n", x7, x8, x9, x15)
        } else ()
      }
      x10.close
    }
    x2.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
