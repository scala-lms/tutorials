/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (Int => Int) {
  def apply(x0: Int): Int = {
    def x1(x2: Int): Int = {
      // ack_0
      x2 + 1
    }
    def x3(x4: Int): Int = {
      // ack_1
      if (x4 == 0) {
        x1(1)
      } else {
        x1(x3(x4 - 1))
      }
    }
    def x5(x6: Int): Int = {
      // ack_2
      if (x6 == 0) {
        x3(1)
      } else {
        x3(x5(x6 - 1))
      }
    }
    x5(x0)
  }
}
/*****************************************
End of Generated Code
*******************************************/
