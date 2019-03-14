/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Word,Value,Phrase,Year,MatchCount,VolumeCount")
    val x2 = new scala.lms.tutorial.Scanner("src/data/words.csv")
    val x3 = x2.next(',')
    val x4 = x2.next('\n')
    val x16 = while (x2.hasNext) {
      val x5 = x2.next(',')
      val x6 = x2.next('\n')
      val x7 = new scala.lms.tutorial.Scanner(x0)
      val x14 = while (x7.hasNext) {
        val x8 = x7.next('\t')
        val x9 = x7.next('\t')
        val x10 = x7.next('\t')
        val x11 = x7.next('\n')
        val x13 = if (true) {
          val x12 = printf("%s,%s,%s,%s,%s,%s\n", x5, x6, x8, x9, x10, x11)
        }
      }
      val x15 = x7.close
    }
    val x17 = x2.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
