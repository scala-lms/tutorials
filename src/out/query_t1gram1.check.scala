/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Phrase,Year,MatchCount,VolumeCount")
    val x2 = new scala.lms.tutorial.Scanner(x0)
    val x4 = while (x2.hasNext) {
      val x3 = printf("%s,%s,%s,%s\n", x2.next('\t'), x2.next('\t'), x2.next('\t'), x2.next('\n'))
    }
    val x5 = x2.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
