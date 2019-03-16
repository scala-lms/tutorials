/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    println("Word,Value,Phrase,Year,MatchCount,VolumeCount")
    val x1 = new scala.lms.tutorial.Scanner("src/data/words.csv")
    x1.next(',')
    x1.next('\n')
    while (x1.hasNext) {
      val x2 = x1.next(',')
      val x3 = x1.next('\n')
      val x4 = new scala.lms.tutorial.Scanner(x0)
      while (x4.hasNext) {
        val x5 = x4.next('\t')
        val x6 = x4.next('\t')
        val x7 = x4.next('\t')
        val x8 = x4.next('\n')
        if (true) printf("%s,%s,%s,%s,%s,%s\n", x2, x3, x5, x6, x7, x8) else {
        }
      }
      x4.close
    }
    x1.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
