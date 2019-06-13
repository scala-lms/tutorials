/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (java.lang.String => Boolean) {
  def apply(x0: java.lang.String): Boolean = {
    var x1 = -1
    var x2 = false
    val x3 = x0.length
    while (!x2 && x1 < x3) {
      val x4 = x1 + 1
      x1 = x4
      x2 = x4 < x3 && 'a' == x0.charAt(x4) && {
        val x5 = x4 + 1
        x5 < x3 && 'b' == x0.charAt(x5)
      }
    }
    x2
  }
}
/*****************************************
End of Generated Code
*******************************************/
