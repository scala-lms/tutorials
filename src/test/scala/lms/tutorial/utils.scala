package scala.lms.tutorial

import java.io._
import org.scalatest.FunSuite

trait TutorialFunSuite extends FunSuite {
  val prefix = "src/out/"
  val under: String
  def readFile(name: String): String = {
    try {
      val buf = new Array[Byte](new File(name).length().toInt)
      val fis = new FileInputStream(name)
      fis.read(buf)
      fis.close()
      new String(buf)
    } catch {
      case e: IOException => ""
    }
  }
  def writeFile(name: String, content: String) {
    val out = new java.io.PrintWriter(new File(name))
    out.write(content)
    out.close()
  }
  def writeFileIndented(name: String, content: String) {
    val out = new java.io.PrintWriter(new File(name))
    printIndented(content)(out)
    out.close()
  }
  def check(label: String, code: String) = {
    val fileprefix = prefix+under+label
    val name = fileprefix+".check.scala"
    val aname = fileprefix+".actual.scala"
    val expected = readFile(name)
    if (expected != code) {
      println("writing " + aname)
      writeFile(aname, code)
    }
    assert(expected == code, name)
  }

  def printIndented(str: String)(out: PrintWriter): Unit = {
    val lines = str.split("[\n\r]")
    var indent = 0
    for (l0 <- lines) {
      val l = l0.trim
      if (l.length > 0) {
        var open = 0
        var close = 0
        var initClose = 0
        var nonWsChar = false
        l foreach {
          case '{' | '(' | '[' => {
            open += 1
            if (!nonWsChar) {
              nonWsChar = true
              initClose = close
            }
          }
          case '}' | ')' | ']' => close += 1
          case x => if (!nonWsChar && !x.isWhitespace) {
            nonWsChar = true
            initClose = close
          }
        }
        if (!nonWsChar) initClose = close
        out.println("  " * (indent - initClose) + l)
        indent += (open - close)
      }
    }
  }

  def exec(label: String, code: String) = {
    val fileprefix = prefix+under+label
    val aname = fileprefix+".actual.scala"
    writeFileIndented(aname, code)
  }
}
