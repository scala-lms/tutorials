/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Phrase,Year,MatchCount,VolumeCount")
    val x2 = new scala.lms.tutorial.Scanner(x0)
    while (x2.hasNext) {
      val x4 = x2.next('\t')
      val x5 = x2.next('\t')
      val x6 = x2.next('\t')
      val x7 = x2.next('\n')
      if (x4 == "Auswanderung") {
        printf("%s,%s,%s,%s\n", x4, x5, x6, x7)
      } else ()
    }
    x2.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
