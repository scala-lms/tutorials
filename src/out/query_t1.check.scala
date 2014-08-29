/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((java.lang.String)=>(Unit)) {
  def apply(x0:java.lang.String): Unit = {
    val x1 = println("Name,Value,Flag")
    val x2 = new scala.lms.tutorial.Scanner("src/data/t.csv", ',')
    val x3 = x2.next
    val x4 = x2.next
    val x5 = x2.next
    val x17 = while ({val x6 = x2.hasNext
      x6}) {
      val x8 = x2.next
      val x9 = x2.next
      val x10 = x2.next
      val x11 = x8+","
      val x12 = x9+","
      val x13 = x12+x10
      val x14 = x11+x13
      val x15 = println(x14)
      x15
    }
    x17
  }
}
/*****************************************
End of Generated Code
*******************************************/
