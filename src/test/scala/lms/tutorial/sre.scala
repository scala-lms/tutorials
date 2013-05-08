package scala.lms.tutorial

import org.scalatest.FunSuite

trait StagedRegexpMatcher extends Dsl {
  /* search for regexp anywhere in text */
  def matchsearch(regexp: String, text: Rep[String]): Rep[Boolean] = {
    if (regexp(0) == '^')
      matchhere(regexp, 1, text, 0)
    else {
      val start = var_new(unit(0))
      val found = var_new(matchhere(regexp, 0, text, start))
      while (!found &&& start<text.length) {
        start += 1
        found = matchhere(regexp, 0, text, start)
      }
      found
    }
  }

  /* search for restart of regexp at start of text */
  def matchhere(regexp: String, restart: Int, text: Rep[String], start: Rep[Int]): Rep[Boolean] = {
    if (restart==regexp.length)
      true
    else if (regexp(restart)=='$' && restart+1==regexp.length)
      start==text.length
    else if (restart+1<regexp.length && regexp(restart+1)=='*')
      matchstar(regexp(restart), regexp, restart+2, text, start)
    else if (start<text.length &&& matchchar(regexp(restart), text(start)))
      matchhere(regexp, restart+1, text, start+1)
    else false
  }

  /* search for c* followed by restart of regexp at start of text */
  def matchstar(c: Char, regexp: String, restart: Int, text: Rep[String], start: Rep[Int]): Rep[Boolean] = {
    val sstart = var_new(start)
    val found = var_new(matchhere(regexp, restart, text, sstart))
    val failed = var_new(unit(false))
    while (!failed &&& !found &&& sstart<text.length) {
      failed = matchchar(c, text(sstart))
      sstart += 1
      found = matchhere(regexp, restart, text, sstart)
    }
    !failed &&& found
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
      //expectResult(expected){snippet.eval(text)}
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
