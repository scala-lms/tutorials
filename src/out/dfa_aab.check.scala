/*****************************************
Emitting Generated Code
*******************************************/
class Snippet() extends (Unit => scala.lms.tutorial.Automaton[Char, Boolean]) {
  def apply(x0: Unit): scala.lms.tutorial.Automaton[Char, Boolean] = {
    var x1: scala.Function1[Char, scala.lms.tutorial.Automaton[Char, Boolean]] = null.asInstanceOf[scala.Function1[Char, scala.lms.tutorial.Automaton[Char, Boolean]]]
    var x2: scala.lms.tutorial.Automaton[Char, Boolean] = null.asInstanceOf[scala.lms.tutorial.Automaton[Char, Boolean]]
    var x3: scala.lms.tutorial.Automaton[Char, Boolean] = null.asInstanceOf[scala.lms.tutorial.Automaton[Char, Boolean]]
    var x4: scala.lms.tutorial.Automaton[Char, Boolean] = null.asInstanceOf[scala.lms.tutorial.Automaton[Char, Boolean]]
    var x5: scala.lms.tutorial.Automaton[Char, Boolean] = null.asInstanceOf[scala.lms.tutorial.Automaton[Char, Boolean]]
    var x6: scala.lms.tutorial.Automaton[Char, Boolean] = null.asInstanceOf[scala.lms.tutorial.Automaton[Char, Boolean]]
    x1 = x7
    x2 = new scala.lms.tutorial.Automaton[Char,Boolean](false, x8)
    x3 = new scala.lms.tutorial.Automaton[Char,Boolean](true, x1)
    x4 = new scala.lms.tutorial.Automaton[Char,Boolean](false, x1)
    def x8(x9:Char): scala.lms.tutorial.Automaton[Char, Boolean] = if (x9 == 'A') x2 else if (x9 == 'B') x3 else x4
    x5 = new scala.lms.tutorial.Automaton[Char,Boolean](false, x8)
    x6 = new scala.lms.tutorial.Automaton[Char,Boolean](false, (x10: Char) => if (x10 == 'A') x5 else x4)
    def x7(x11:Char): scala.lms.tutorial.Automaton[Char, Boolean] = if (x11 == 'A') x6 else x4
    new scala.lms.tutorial.Automaton[Char,Boolean](false, x7)
  }
}
/*****************************************
End of Generated Code
*******************************************/
