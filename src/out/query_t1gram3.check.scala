/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Unit) {
  def apply(x0: java.lang.String): Unit = {
    val x1 = println("Word,Value,Phrase,Year,MatchCount,VolumeCount")
    val x2 = new scala.lms.tutorial.Scanner("src/data/words.csv")
    x2.next(',')
    x2.next('\n')
    while (x2.hasNext) {
      val x6 = x2.next(',')
      val x7 = x2.next('\n')
      val x8 = new scala.lms.tutorial.Scanner(x0)
      while (x8.hasNext) {
        val x10 = x8.next('\t')
        val x11 = x8.next('\t')
        val x12 = x8.next('\t')
        val x13 = x8.next('\n')
        if (true) {
          printf("%s,%s,%s,%s,%s,%s\n", x6, x7, x10, x11, x12, x13)
        } else ()
      }
      x8.close
    }
    x2.close
  }
}
/*****************************************
End of Generated Code
*******************************************/
