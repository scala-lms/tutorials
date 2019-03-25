/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (java.lang.String => Boolean) {
  def apply(x0: java.lang.String): Boolean = {
    val x1 = x0.length
    ((0 < x1) && {
      'a' == (x0.charAt(0))
    }) && {
      var x2 = 1
      var x3 = true
      var x4 = false
      while ({
        ((!x4) && {
          !x3
        }) && {
          x2 < x1
        }
      }) {
        x4 = !('b' == (x0.charAt(x2)))
        x2 = x2 + 1
        x3 = true
      }
      (!x4) && {
        x3
      }
    }
  }
}
/*****************************************
End of Generated Code
*******************************************/
