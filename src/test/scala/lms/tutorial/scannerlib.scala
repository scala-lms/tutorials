package scala.lms.tutorial

import java.io.FileReader
import java.io.BufferedReader

class Scanner(filename: String) {
  val br = new BufferedReader(new FileReader(filename))
  var pending: String = ""
  def next(delim: Char): String = {
    val i = pending.indexOf(delim)
    if (i == -1) {
      pending = br.readLine()+'\n'
      next(delim)
    } else {
      val (field,rest) = pending.splitAt(i)
      pending = rest.substring(1)
      field
    }
  }
  def hasNext = pending.nonEmpty || br.ready
  def close = br.close
}
