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
      var x5 = x4
      var x6 = x4 < x3 && 'b' == x0.charAt(x4)
      var x7 = false
      while (!x7 && !x6 && x5 < x3) {
        x7 = !('a' == x0.charAt(x5))
        val x8 = x5 + 1
        x5 = x8
        x6 = x8 < x3 && 'b' == x0.charAt(x8)
      }
      x2 = !x7 && x6
    }
    x2
  }
}
/*****************************************
End of Generated Code
*******************************************/
