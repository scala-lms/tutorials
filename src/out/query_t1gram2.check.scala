/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((java.lang.String)=>(Unit)) {
  def apply(x0:java.lang.String): Unit = {
    val x1 = println("Phrase,Year,MatchCount,VolumeCount")
    val x2 = new scala.lms.tutorial.Scanner(x0, '	')
    val x20 = while ({val x3 = x2.hasNext
      x3}) {
      val x5 = x2.next
      val x6 = x2.next
      val x7 = x2.next
      val x8 = x2.next
      val x9 = x5 == "Auswanderung"
      val x18 = if (x9) {
        val x10 = x5+","
        val x11 = x6+","
        val x12 = x7+","
        val x13 = x12+x8
        val x14 = x11+x13
        val x15 = x10+x14
        val x16 = println(x15)
        x16
      } else {
        ()
      }
      x18
    }
    x20
  }
}
/*****************************************
End of Generated Code
*******************************************/
