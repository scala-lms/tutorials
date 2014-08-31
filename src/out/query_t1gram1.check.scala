/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((java.lang.String)=>(Unit)) {
  def apply(x0:java.lang.String): Unit = {
    val x1 = println("Phrase,Year,MatchCount,VolumeCount")
    val x2 = new scala.lms.tutorial.Scanner(x0, '	')
    val x11 = while ({val x3 = x2.hasNext
      x3}) {
      val x5 = x2.next
      val x6 = x2.next
      val x7 = x2.next
      val x8 = x2.next
      val x9 = printf("%s,%s,%s,%s\n",x5,x6,x7,x8)
      x9
    }
    x11
  }
}
/*****************************************
End of Generated Code
*******************************************/
