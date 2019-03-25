/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (Unit => scala.lms.tutorial.Automaton[Char, Boolean]) {
  def apply(x1: Unit): scala.lms.tutorial.Automaton[Char, Boolean] = {
    var x2: scala.Function1[Char, scala.lms.tutorial.Automaton[Char, Boolean]] = null
    var x20: scala.lms.tutorial.Automaton[Char, Boolean] = null
    var x24: scala.lms.tutorial.Automaton[Char, Boolean] = null
    var x26: scala.lms.tutorial.Automaton[Char, Boolean] = null
    var x29: scala.lms.tutorial.Automaton[Char, Boolean] = null
    var x32: scala.lms.tutorial.Automaton[Char, Boolean] = null
    x2 = x3
    x20 = new scala.lms.tutorial.Automaton[Char,Boolean](false, x15)
    x24 = new scala.lms.tutorial.Automaton[Char,Boolean](true, x2)
    x26 = new scala.lms.tutorial.Automaton[Char,Boolean](false, x2)
    def x15(x17: Char): scala.lms.tutorial.Automaton[Char, Boolean] = {
      if (x17 == 'A') {
        x20
      } else {
        if (x17 == 'B') {
          x24
        } else {
          x26
        }
      }
    }
    x29 = new scala.lms.tutorial.Automaton[Char,Boolean](false, x15)
    def x9(x11: Char): scala.lms.tutorial.Automaton[Char, Boolean] = {
      if (x11 == 'A') {
        x29
      } else {
        x26
      }
    }
    x32 = new scala.lms.tutorial.Automaton[Char,Boolean](false, x9)
    def x3(x5: Char): scala.lms.tutorial.Automaton[Char, Boolean] = {
      if (x5 == 'A') {
        x32
      } else {
        x26
      }
    }
    new scala.lms.tutorial.Automaton[Char,Boolean](false, x3)
  }
}
/*****************************************
End of Generated Code
*******************************************/
