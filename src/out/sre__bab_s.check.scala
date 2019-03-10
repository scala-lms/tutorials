/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends (java.lang.String => Boolean) {
  def apply(x0: java.lang.String): Boolean = {
    val x1 = x0.length
    0 < x1 && 'a' == x0.charAt(0) && {
      var x2 = 0 + 1
      x2
      var x4 = true
      var x5 = false
      while (!x5 && !x4 && x2 < x1) {
        x5 = !('b' == x0.charAt(x2))
        x2 = x2 + 1
        x2
        x4 = true
      }
      !x5 && x4
    }
  }
}
/*****************************************
End of Generated Code
*******************************************/
