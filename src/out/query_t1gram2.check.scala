/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Phrase,Year,MatchCount,VolumeCount")
    val x1 = new scala.lms.tutorial.Scanner("src/data/t1gram.csv")
    while (x1.hasNext) {
      val x2 = x1.next('\t')
      val x3 = x1.next('\t')
      val x4 = x1.next('\t')
      val x5 = x1.next('\n')
      if (x2 == "Auswanderung") {
        printf("%s,%s,%s,%s\n", x2, x3, x4, x5)
      }
    }
    x1.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
