/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((java.lang.String)=>(Unit)) {
  def apply(x0:java.lang.String): Unit = {
    val x1 = println("Name,Value,Flag,Name")
    val x2 = new scala.lms.tutorial.Scanner("src/data/t.csv", ',')
    val x3 = x2.next
    val x4 = x2.next
    val x5 = x2.next
    val x28 = while ({val x6 = x2.hasNext
      x6}) {
      val x8 = x2.next
      val x9 = x2.next
      val x10 = x2.next
      val x11 = new scala.lms.tutorial.Scanner("src/data/t.csv", ',')
      val x12 = x11.next
      val x13 = x11.next
      val x14 = x11.next
      val x26 = while ({val x15 = x11.hasNext
        x15}) {
        val x17 = x11.next
        val x18 = x11.next
        val x19 = x11.next
        val x20 = x8 == x17
        val x21 = true && x20
        val x24 = if (x21) {
          val x22 = printf("%s,%s,%s,%s\n",x8,x9,x10,x17)
          x22
        } else {
          ()
        }
        x24
      }
      x26
    }
    x28
  }
}
/*****************************************
End of Generated Code
*******************************************/
