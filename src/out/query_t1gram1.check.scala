/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Phrase,Year,MatchCount,VolumeCount")
    val x1 = new scala.lms.tutorial.Scanner("src/data/t1gram.csv")
    while (x1.hasNext) {
      printf("%s,%s,%s,%s\n", x1.next('\t'), x1.next('\t'), x1.next('\t'), x1.next('\n'))
    }
    x1.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
