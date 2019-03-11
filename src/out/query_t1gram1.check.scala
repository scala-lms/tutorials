/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Phrase,Year,MatchCount,VolumeCount")
    val x2 = new scala.lms.tutorial.Scanner(x0)
    while (x2.hasNext) {
      printf("%s,%s,%s,%s\n", x2.next('\t'), x2.next('\t'), x2.next('\t'), x2.next('\n'))
    }
    x2.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
