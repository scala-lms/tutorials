package scala.lms.tutorial

import java.io._
import org.scalatest.FunSuite

trait TutorialFunSuite extends FunSuite {
  val overwriteCheckFiles = false // should be false; temporary set to true only to simplify development

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
  def checkOut(label: String, suffix: String, thunk: => Unit) = {
    val output = new ByteArrayOutputStream()
    scala.Console.setOut(new PrintStream(output))
    thunk
    check(label, output.toString(), suffix = suffix)
  }
  def check(label: String, raw_code: String, suffix: String = "scala") = {
    val fileprefix = prefix+under+label
    val name = fileprefix+".check."+suffix
    val aname = fileprefix+".actual."+suffix
    val expected = readFile(name)
    val code = indent(raw_code)
    if (expected != code) {
      val wname = if (overwriteCheckFiles) name else aname
      println("writing " + wname)
      writeFile(wname, code)
    } else {
      val f = new File(aname)
      if (f.exists) f.delete
    }
    if (!overwriteCheckFiles) {
      assert(expected == code, name)
    }
  }
  def indent(str: String) = {
    val s = new StringWriter
    printIndented(str)(new PrintWriter(s))
    s.toString
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
          case '{' => {
            open += 1
            if (!nonWsChar) {
              nonWsChar = true
              initClose = close
            }
          }
          case '}' => close += 1
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
    assert (indent==0, "indentation sanity check")
  }

  def exec(label: String, code: String) = {
    val fileprefix = prefix+under+label
    val aname = fileprefix+".actual.scala"
    writeFileIndented(aname, code)
  }
}
