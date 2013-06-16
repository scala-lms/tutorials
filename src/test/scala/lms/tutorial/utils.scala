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
  def exec(label: String, code: String) = {
    val fileprefix = prefix+under+label
    val aname = fileprefix+".actual.scala"
    writeFile(aname, code)
  }
}
