/**
# From Functions to Automata: Regex take 2

Outline:
<div id="tableofcontents"></div>


Specializing string matchers and parsers is a popular benchmark in the partial
evaluation and supercompilation literature
[(*)](DBLP:journals/ipl/ConselD89,DBLP:journals/toplas/AgerDR06,DBLP:journals/toplas/SperberT00,DBLP:journals/toplas/Turchin86,DBLP:journals/jfp/SorensenGJ96).
We consider ``multi-threaded'' regular expression matchers, that spawn a new
conceptual thread to process alternatives in parallel. Of course these matchers
do not actually spawn OS-level threads, but rather need to be advanced manually
by client code. Thus, they are similar to coroutines.

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

The LMS framework memoizes  functions (see [here](#sec:220functions)) which
ensures  termination if the NFA is indeed finite.

We use a separate function to explore the NFA space (see
Figure~\ref{fig:NFAexplore}), advancing the automaton by a symbolic character
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

The generated code is shown in Figure~\ref{fig:regexGen}. Each function
corresponds to one DFA state. Note how  negative information has been used to
prune the transition space: Given input such as `...AAB` the  automaton jumps
back to the initial state, i.e. it recognizes that the last character B
cannot also be A and starts looking for two As after the B.


The generated code can be used as follows:

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
