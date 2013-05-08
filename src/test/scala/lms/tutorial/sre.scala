package scala.lms.tutorial

import org.scalatest.FunSuite

trait StagedRegexpMatcher extends Dsl {
  def for_ret(default: Rep[Boolean])(start: Rep[Int], end: Rep[Int])(m: Map[Rep[Int => Boolean], Boolean]): Rep[Boolean] = {
    lazy val loop: Rep[Int => Boolean] = fun {(i: Rep[Int]) =>
      if (i > end) default else {
        def next(m: Map[Rep[Int => Boolean], Boolean]): Rep[Boolean] = {
          if (m.isEmpty) loop(i+1) else {
            val (f, r) = m.head
            if (f(i)) r else next(m.tail)
          }
        }
        next(m)
      }
    }
    loop(start)
  }
  /* search for regexp anywhere in text */
  def matchsearch(regexp: String, text: Rep[String]): Rep[Boolean] = {
    if (regexp(0) == '^')
      matchhere(regexp.substring(1), text)
    else
      for_ret(false)(0, text.length)(Map(
        fun {(i: Rep[Int]) => matchhere(regexp, text.substring(i))} -> true
      ))
  }

  /* search for regexp at beginning of text */
  def matchhere(regexp: String, text: Rep[String]): Rep[Boolean] = {
    if (regexp.isEmpty)
      true
    else if (regexp == "$")
      text.isEmpty
    else if (regexp.length>1 && regexp(1)=='*')
      matchstar(regexp(0), regexp.substring(2), text)
    else if (!text.isEmpty)
      if (matchchar(regexp(0), text(0)))
        matchhere(regexp.substring(1), text.substring(1))
      else false
    else false
  }

  /* search for c*regexp at the beginning of text */
  def matchstar(c: Char, regexp: String, text: Rep[String]): Rep[Boolean] = {
    for_ret(false)(0, text.length)(Map(
      fun {(i: Rep[Int]) => matchhere(regexp, text.substring(i))} -> true,
      fun {(i: Rep[Int]) => if (i < text.length) !matchchar(c, text(i)) else false} -> false
    ))
  }

  def matchchar(c: Char, t: Rep[Char]): Rep[Boolean] = {
    c == '.' || c == t
  }
}

class StagedRegexpMatcherTest extends TutorialFunSuite {
  val under = "sre"
  var m = Map.empty[String, DslDriver[String,Boolean]]
  def cache(k: String, b: => DslDriver[String,Boolean]): DslDriver[String,Boolean] = {
    m.get(k) match {
      case Some(v) => v
      case None =>
        m = m.updated(k, b)
        m(k)
    }
  }
  def testmatch(regexp: String, text: String, expected: Boolean) {
    test(s"""matchsearch("$regexp", "$text") == $expected""") {
      val snippet = cache(regexp,
        new DslDriver[String,Boolean] with StagedRegexpMatcher {
          def snippet(x: Rep[String]) = matchsearch(regexp, x)
        })
      expectResult(expected){snippet.eval(text)}
      check("_"+regexp.replace("^", "_b").replace("*", "_s").replace("$", "_e"),
            snippet.code)
    }
  }

  testmatch("^hello$", "hello", true)
  testmatch("^hello$", "hell", false)
  testmatch("hell", "hello", true);
  testmatch("hell", "hell", true);
  testmatch("hel*", "he", true);
  testmatch("hel*$", "hello", false);
  testmatch("hel*", "yo hello", true);
  testmatch("ab", "hello ab hello", true);
  testmatch("^ab", "hello ab hello", false);
  testmatch("a*b", "hello aab hello", true);
}
