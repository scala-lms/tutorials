/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Phrase,Year,MatchCount,VolumeCount")
    val x2 = new scala.lms.tutorial.Scanner(x0)
    while (x2.hasNext) {
      val x3 = x2.next('\t')
      val x4 = x2.next('\t')
      val x5 = x2.next('\t')
      val x6 = x2.next('\n')
      if (x3 == "Auswanderung") {
        printf("%s,%s,%s,%s\n", x3, x4, x5, x6)
      }
    }
    x2.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
