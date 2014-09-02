package scala.lms.tutorial

import java.io.FileReader
import java.io.BufferedReader

class Scanner(filename: String, fieldDelimiter: Char) {
  val br = new BufferedReader(new FileReader(filename))
  var pending: List[String] = Nil
  def next: String = pending match {
    case Nil =>
      pending = br.readLine.split(fieldDelimiter).toList
      next
    case field::rest =>
      pending = rest
      field
  }
  def hasNext = pending.nonEmpty || br.ready || {br.close; false}
  def hasNextInLine = pending.nonEmpty
  def close = br.close
}

class ScannerLib(filename: String) {
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
  def hasNext = pending.nonEmpty || br.ready || {br.close; false}
  def close = br.close
}
