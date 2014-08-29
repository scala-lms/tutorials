/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((java.lang.String)=>(Unit)) {
  def apply(x0:java.lang.String): Unit = {
    val x1 = println("Phrase,Year,MatchCount,VolumeCount")
    val x2 = new scala.lms.tutorial.Scanner(x0, '	')
    val x17 = while ({val x3 = x2.hasNext
      x3}) {
      val x5 = x2.next
      val x6 = x2.next
      val x7 = x2.next
      val x8 = x2.next
      val x9 = x5+","
      val x10 = x6+","
      val x11 = x7+","
      val x12 = x11+x8
      val x13 = x10+x12
      val x14 = x9+x13
      val x15 = println(x14)
      x15
    }
    x17
  }
}
/*****************************************
End of Generated Code
*******************************************/
