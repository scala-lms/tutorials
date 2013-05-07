package scala.lms.tutorial

import org.scalatest.FunSuite

// translated to Scala from
// http://www.cs.princeton.edu/courses/archive/spr09/cos333/beautiful.html
trait RegexpMatcher {
  /* search for regexp anywhere in text */
  def matchsearch(regexp: String, text: String): Boolean = {
    if (regexp(0) == '^')
      return matchhere(regexp.substring(1), text)
    for (i <- 0 to text.length)
      if (matchhere(regexp, text.substring(i)))
        return true
    return false
  }

  /* search for regexp at beginning of text */
  def matchhere(regexp: String, text: String): Boolean = {
    if (regexp.isEmpty)
      return true
    if (regexp == "$")
      return text.isEmpty
    if (regexp.length>1 && regexp(1)=='*')
      return matchstar(regexp(0), regexp.substring(2), text)
    if (!text.isEmpty && matchchar(regexp(0), text(0)))
      return matchhere(regexp.substring(1), text.substring(1))
    return false
  }

  /* search for c*regexp at the beginning of text */
  def matchstar(c: Char, regexp: String, text: String): Boolean = {
    for (i <- 0 to text.length) {
      if (matchhere(regexp, text.substring(i)))
        return true
      if (i < text.length && !matchchar(c, text(i)))
        return false
    }
    return false
  }

  def matchchar(c: Char, t: Char): Boolean = {
    c == '.' || c == t
  }
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
}
