package scala.lms.tutorial

import org.scalatest.FunSuite

// translated to Scala from
// http://www.cs.princeton.edu/courses/archive/spr09/cos333/beautiful.html
trait RegexpMatcher {
  //#matchsearch
  /* search for regexp anywhere in text */
  def matchsearch(regexp: String, text: String): Boolean = {
    if (regexp(0) == '^')
      matchhere(regexp, 1, text, 0)
    else {
      var start = -1
      var found = false
      while (!found && start<text.length) {
        start += 1
        found = matchhere(regexp, 0, text, start)
      }
      found
    }
  }
  //#matchsearch

  //#matchhere
  /* search for restart of regexp at start of text */
  def matchhere(regexp: String, restart: Int, text: String, start: Int): Boolean = {
    if (restart==regexp.length)
      true
    else if (regexp(restart)=='$' && restart+1==regexp.length)
      start==text.length
    else if (restart+1<regexp.length && regexp(restart+1)=='*')
      matchstar(regexp(restart), regexp, restart+2, text, start)
    else if (start<text.length && matchchar(regexp(restart), text(start)))
      matchhere(regexp, restart+1, text, start+1)
    else false
  }
  //#matchhere

  //#matchstar
  /* search for c* followed by restart of regexp at start of text */
  def matchstar(c: Char, regexp: String, restart: Int, text: String, start: Int): Boolean = {
    var sstart = start
    var found = matchhere(regexp, restart, text, sstart)
    var failed = false
    while (!failed && !found && sstart<text.length) {
      failed = matchchar(c, text(sstart))
      sstart += 1
      found = matchhere(regexp, restart, text, sstart)
    }
    !failed && found
  }
  //#matchstar

  //#matchchar
  def matchchar(c: Char, t: Char): Boolean = {
    c == '.' || c == t
  }
  //#matchchar
}

class RegexpMatcherTest extends RegexpMatcher with FunSuite {
  def testmatch(regexp: String, text: String, expected: Boolean) {
    test(s"""matchsearch("$regexp", "$text") == $expected""") {
      expectResult(expected){matchsearch(regexp, text)}
    }
  }

  testmatch("^hello$", "hello", true)
  testmatch("^hello$", "hell", false)
  testmatch("hell", "hello", true);
  testmatch("hell", "hell", true);
  testmatch("hel*", "he", true);
  testmatch("hel*$", "hello", false);
  testmatch("hel*", "yo hello", true);
  testmatch("a*b", "hello aab hello", true);
}
