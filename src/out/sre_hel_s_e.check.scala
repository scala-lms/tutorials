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
      val x5 = x1
      x2 = x5 < x3 && 'h' == x0.charAt(x5) && {
        val x6 = x5 + 1
        x6 < x3 && 'e' == x0.charAt(x6) && {
          var x7 = x6 + 1
          var x8 = x7 == x3
          var x9 = false
          while (!x9 && !x8 && x7 < x3) {
            x9 = !('l' == x0.charAt(x7))
            x7 = x7 + 1
            x8 = x7 == x3
          }
          !x9 && x8
        }
      }
    }
    x2
  }
}
/*****************************************
End of Generated Code
*******************************************/
