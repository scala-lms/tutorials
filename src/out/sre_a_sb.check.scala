/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Boolean) {
  def apply(x0: java.lang.String): Boolean = {
    var x1 = -1
    var x2 = false
    val x3 = x0.length
    while (!x2 && x1 < x3) {
      x1 = x1 + 1
      var x5 = x1
      val x6 = x5
      var x7 = x6 < x3 && 'b' == x0.charAt(x6) && true
      var x8 = false
      while (!x8 && !x7 && x5 < x3) {
        x8 = !('a' == x0.charAt(x5))
        x5 = x5 + 1
        val x10 = x5
        x7 = x10 < x3 && 'b' == x0.charAt(x10) && true
      }
      x2 = !x8 && x7
    }
    x2
  }
}
/*****************************************
End of Generated Code
*******************************************/
