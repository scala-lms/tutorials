/**
# Automata-Based Regex Matcher

Outline:
<div id="tableofcontents"></div>


Specializing string matchers and parsers is a popular benchmark in the partial
evaluation and supercompilation literature.

<!--
- http://dblp.uni-trier.de/db/ journals/ipl/ConselD89
- http://dblp.uni-trier.de/db/ journals/toplas/AgerDR06
- http://dblp.uni-trier.de/db/ journals/toplas/SperberT00
- http://dblp.uni-trier.de/db/ journals/toplas/Turchin86,
- http://dblp.uni-trier.de/db/ journals/jfp/SorensenGJ96
-->

We consider ``multi-threaded'' regular expression matchers, that spawn a new
conceptual thread to process alternatives in parallel. Of course these matchers
do not actually spawn OS-level threads, but rather need to be advanced manually
by client code. Thus, they are similar to coroutines.
*/

package scala.lms.tutorial

case class Automaton[@specialized(Char) I, @specialized(Boolean) O](out: O, next: I => Automaton[I,O])

trait DFAOps extends Dsl {
  implicit def dfaTyp: Typ[DfaState]
  type DfaState = Automaton[Char,Boolean]
  type DIO = Rep[DfaState]
  def dfa_trans(f: Rep[Char] => DIO): DIO = dfa_trans(false)(f)
  def dfa_trans(e: Boolean)(f: Rep[Char] => DIO): DIO
}

trait DFAOpsExp extends DslExp with DFAOps {
  implicit def dfaTyp: Typ[DfaState] = manifestTyp
  case class DFAState(e: Boolean, f: Rep[Char => DfaState]) extends Def[DfaState]
  def dfa_trans(e: Boolean)(f: Rep[Char] => DIO): DIO = DFAState(e, doLambda(f))
}

trait ScalaGenDFAOps extends DslGen {
  val IR: DFAOpsExp
  import IR._
  override def emitNode(sym: Sym[Any], rhs: Def[Any]) = rhs match {
    case dfa@DFAState(b,f) =>
      emitValDef(sym, "scala.lms.tutorial.Automaton(" + quote(b) + ", " + quote(f) + ")")
    case _ => super.emitNode(sym, rhs)
  }
}
abstract class AutomataDriver extends DslDriver[Unit,Automaton[Char,Boolean]] with DFAOpsExp { q =>
  override val codegen = new ScalaGenDFAOps {
    val IR: q.type = q
  }
  def matches(s: String): Boolean = {
    var a: Automaton[Char,Boolean] = f(())
    var i: Int = 0
    while (!a.out && i < s.length) {
      a = a.next(s(i))
      i += 1
    }
    a.out
  }
}

trait NFAtoDFA extends DFAOps with scala.lms.util.ClosureCompare {
  type NIO = List[NTrans]
  
  case class NTrans(c: CharSet, e: () => Boolean, s: () => NIO) extends Ordered[NTrans] {
    override def compare(o: NTrans) = {
      val i = this.c.compare(o.c)
      if (i != 0) i else {
        val i2 = this.e().compare(o.e())
        if (i2 != 0) i2 else {
          val tf = canonicalize(this.s())
          val of = canonicalize(o.s())
          if (tf == of) 0 else tf.compare(of)
	}
      }
    }
  }

  def trans(c: CharSet)(s: () => NIO): NIO = List(NTrans(c, () => false, s))
  def guard(cond: CharSet, found: => Boolean = false)(e: => NIO): NIO = {
    List(NTrans(cond, () => found, () => e))
  }
  def guards(conds: List[CharSet], found: Boolean = false)(e: => NIO): NIO = {
    conds.flatMap(guard(_, found)(e))
  }

  def stop(): NIO = Nil
  
  sealed abstract class CharSet extends Ordered[CharSet] {
    override def compare(o: CharSet) = (this,o) match {
      case (W,W) => 0
      case (W,_) => 1
      case (_,W) => -1
      case (R(a1,b1),R(a2,b2)) if a1 == a2 => b1.compare(b2)
      case (R(a1,_),R(a2,_)) => a1.compare(a2)
      case (R(_,_),C(_)) => -1
      case (C(_),R(_,_)) => 1
      case (C(c1),C(c2)) => c1.compare(c2)
    }
  }
  case class R(a: Char, b: Char) extends CharSet
  case class C(c: Char) extends CharSet
  case object W extends CharSet

  def r(a: Char, b: Char) = {
    assert(a <= b)
    if (a == b) C(a) else R(a, b)
  }

  def infix_contains(s: CharSet, c: Rep[Char]): Rep[Boolean] = s match {
    case C(c1) => c == c1
    case R(a, b) => a <= c && c <= b
    case W => unit(true)
  }
  def infix_knowing(s1: CharSet, s2: CharSet): Option[CharSet] = (s1,s2) match {
    case (W,_) => Some(W)
    case (C(c1),C(c2)) if c1 == c2 => Some(W)
    case (R(a1,b1),R(a2,b2)) if a2 <= a1 && b1 <= b2 => Some(W)
    case (C(c1),R(a2,b2)) if a2 <= c1 && c1 <= b2 => Some(W)
    case (R(a1,b1),R(a2,b2)) if a1 <= a2 && a2 <= b1 && b1 <= b2 => Some(r(a2, b1))
    case (R(a1,b1),R(a2,b2)) if a2 <= a1 && a1 <= b2 && b2 <= b1 => Some(r(a1, b2))
    case (R(a1,b1),C(c2)) if a1 <= c2 && c2 <= b1 => Some(s1)
    case _ => None
  }
  def infix_knowing_not(s1: CharSet, s2: CharSet): Option[CharSet] = (s1,s2) match {
    case (C(c1), C(c2)) if c1 == c2 => None
    case (R(a1,b1),R(a2,b2)) if a2 <= a1 && b1 <= b2 => None
    case (C(c1),R(a2,b2)) if a2 <= c1 && c1 <= b2 => None
    case (R(a1,b1),R(a2,b2)) if a1 <= a2 && a2 <= b1 && b1 <= b2 => Some(r(a1, a2))
    case (R(a1,b1),R(a2,b2)) if a2 <= a1 && a1 <= b2 && b2 <= b1 => Some(r(b2, b1))
    case _ => Some(s1)
  }

  def exploreNFA[A:Typ](xs: NIO, cin: Rep[Char])(k: (Boolean, NIO) => Rep[A]): Rep[A] = xs match {
    case Nil => k(false, Nil)
    case NTrans(W, e, s)::rest =>
      val (xs1, xs2) = xs.partition(_.c != W)
      exploreNFA(xs1,cin)((flag,acc) => k(flag || xs2.exists(_.e()), acc ++ xs2.flatMap(_.s())))
    case NTrans(cset, e, s)::rest =>
      if (cset contains cin) {
        val xs1 = for (NTrans(rcset, re, rs) <- rest;
		       kcset <- rcset knowing cset) yield
			 NTrans(kcset,re,rs)
        exploreNFA(xs1,cin)((flag,acc) => k(flag || e(), acc ++ s()))
      } else {
        val xs1 = for (NTrans(rcset, re, rs) <- rest;
		       kcset <- rcset knowing_not cset) yield
			 NTrans(kcset,re,rs)
        exploreNFA(xs1, cin)(k)
      }
  }


  def convertNFAtoDFA(in: (NIO, Boolean)): DIO = {
    def iterate(flag: Boolean, state: NIO): DIO = {
      val state_cooked = if (state.isEmpty) state else {
        val state_sorted = state.sorted
        state_sorted.head :: (for ((s,sn) <- (state_sorted zip state_sorted.tail)
             if s.compare(sn) != 0) yield sn)
      }
      dfa_trans(flag){ c: Rep[Char] => exploreNFA(state_cooked, c) { iterate }
    }}
    iterate(in._2, in._1)
  }
}

class AutomataTest extends TutorialFunSuite {
  val under = "dfa_"

  test("findAAB") {
    val p = new AutomataDriver with NFAtoDFA {
      def snippet(x: Rep[Unit]) = {
        def findAAB(): NIO = {
          guard(C('A')) {
            guard(C('A')) {
              guard(C('B'), true) {
                stop()
          }}} ++
          guard(W) { findAAB() } // in parallel ...
        }
        convertNFAtoDFA((findAAB(), false))
      }
    }
    assertResult(true){p.matches("AAB")}
    assertResult(false){p.matches("AAC")}
    assertResult(true){p.matches("AACAAB")}
    assertResult(true){p.matches("AACAABAAC")}
    check("aab", p.code)
  }
}

/**
Regexp Matchers as Nondeterministic Finite Automata (NFA)
---------------------------------------------------------

Here is a simple example for the fixed regular expression `.*AAB`:

    def findAAB(): NIO = {
      guard(Set('A')) {
        guard(Set('A')) {
          guard(Set('B'), Found)) {
            stop()
      }}} ++
      guard(None) { findAAB() } // in parallel...
    }

We can easily add combinators on top of the core abstractions that take care
of producing matchers from textual regular expressions. However  the point
here is to demonstrate how the implementation works.

The given matcher uses an API that models nondeterministic finite automata
(NFA):

    type NIO = List[Trans]   // state: many possible transitions
    case class Trans(c: Set[Char], x: Flag, s: () => NIO)

    def guard(cond: Set[Char], flag: Flag)(e: => NIO): NIO =
      List(Trans(cond, flag, () => e))
    def stop(): NIO = Nil

An NFA state consists of a list of possible transitions. Each transition may
be guarded by a set of characters and it may  have a flag to be signaled if
the transition is taken. It also knows how to compute the following state. We
use `Char`s for simplicity, but of course we could  use generic types as well.
Note that the API does not mention where input is obtained from (files,
streams, etc).


From NFA to DFA using Staging
-----------------------------

We will translate NFAs to DFAs using staging. This is the unstaged DFA API:

    abstract class DfaState {
      def hasFlag(x: Flag): Boolean
      def next(c: Char): DfaState
    }
    def dfaFlagged(flag: Flag, link: DfaState) = new DfaState {
      def hasFlag(x: Flag) = x == flag || link.hasFlag(x)
      def next(c: Char) = link.next(c)
    }
    def dfaState(f: Char => DfaState) = new DfaState {
      def hasFlag(x: Flag) = false
      def next(c: Char) = f(c)
    }


The staged API is just a thin wrapper:

    type DIO = Rep[DfaState]
    def dfa_flag(x: Flag)(link: DIO): DIO
    def dfa_trans(f: Rep[Char] => DIO): DIO


Translating an NFA to a DFA is accomplished by creating a DFA state for each
encountered NFA configuration (removing duplicate states via `canonicalize`):

    def convertNFAtoDFA(states: NIO): DIO = {
      val cstates = canonicalize(state)
      dfa_trans { c: Rep[Char] =>
        exploreNFA(cstates, c)(dfa_flag) { next =>
          convertNFAtoDFA(next)
        }
      }
    }
    iterate(findAAB())

The LMS framework memoizes  functions (see [here (PDF)](http://infoscience.epfl.ch/record/178274/files/lms-cacm2012.pdf)) which
ensures  termination if the NFA is indeed finite.

We use a separate function to explore the NFA space (see
below), advancing the automaton by a symbolic character
`cin` to invoke its continuations `k` with a new automaton, i.e. the possible
set of states after consuming `cin`. The given implementation assumes
character sets contain either zero or one characters, the empty set `Set()`
denoting a wildcard match. More elaborate cases such as character ranges are
easy to add. The algorithm tries to remove as many redundant checks and
impossible branches as possible. This only works because the character guards
are staging time values.

    def exploreNFA[A](xs: NIO, cin: Rep[Char])(flag: Flag => Rep[A] => Rep[A])
                                              (k: NIO => Rep[A]):Rep[A] = xs match {
      case Nil => k(Nil)
      case Trans(Set(c), e, s)::rest =>
        if (cin == c) {
          // found match: drop transitions that look for other chars and
          // remove redundant checks
          val xs1 = rest collect { case Trans(Set(`c`)|None,e,s) => Trans(Set(),e,s) }
          val maybeFlag = e map flag getOrElse (x=>x)
          maybeFlag(exploreNFA(xs1, cin)(acc => k(acc ++ s())))
        } else {
          // no match, drop transitions that look for same char
          val xs1 = rest filter { case Trans(Set(`c`),_,_) => false case _ => true }
          exploreNFA(xs1, cin)(k)
        }
      case Trans(Set(), e, s)::rest =>
        val maybeFlag = e map flag getOrElse (x=>x)
        maybeFlag(exploreNFA(rest, cin)(acc => k(acc ++ s())))
    }

The generated code is shown further down below. Each function
corresponds to one DFA state. Note how  negative information has been used to
prune the transition space: Given input such as `...AAB` the  automaton jumps
back to the initial state, i.e. it recognizes that the last character B
cannot also be A and starts looking for two As after the B.


Generated State Machine Code
----------------------------

To use the generated code, we use the following small driver loop:

    var state = stagedFindAAB()
    var input = ...
    while (input.nonEmpty) {
      state = state.next(input.head)
      if (state.hasFlag(Found))
        println("found AAB. rest: " + input.tail)
      input = input.tail
    }


If the matcher and input iteration logic is generated together, further
translations can be applied to transform the mutually recursive lambdas into
tight imperative state machines.


    def stagedFindAAB(): DfaState = {
      val x7 = { x8: (Char) =>
        // matched AA
        val x9 = x8 == B
        val x15 = if (x9) {
          x11
        } else {
          val x12 = x8 == A
          val x14 = if (x12) {
            x13
          } else {
            x10
          }
          x14
        }
        x15
      }
      val x13 = dfaState(x7)
      val x4 = { x5: (Char) =>
        // matched A
        val x6 = x5 == A
        val x16 = if (x6) {
          x13
        } else {
          x10
        }
        x16
      }
      val x17 = dfaState(x4)
      val x1 = { x2: (Char) =>
        // matched nothing
        val x3 = x2 == A
        val x18 = if (x3) {
          x17
        } else {
          x10
        }
        x18
      }
      val x10 = dfaState(x1)
      val x11 = dfaFlagged(Found, x10)
      x10
    }

Generated matcher code for regular expression `.*AAB`

*/
