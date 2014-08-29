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
