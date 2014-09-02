package scala.lms.tutorial

import scala.virtualization.lms.common._
import scala.reflect.SourceContext

// tests for the non-staged Scanner library
// provided by scannerlib.scala
// which is in a separate file so that it can easily be included
// independently of the whole project
class ScannerLibTest extends LibSuite {
  test("low-level first field scanning") {
    val s = new ScannerLib(dataFilePath("t.csv"))
    assert(s.next(',')=="Name")
    s.close
  }
  test("low-level first 2 fields scanning") {
    val s = new ScannerLib(dataFilePath("t.csv"))
    assert(s.next(',')=="Name")
    assert(s.next(',')=="Value")
    s.close
  }
  test("low-level first line scanning") {
    val s = new ScannerLib(dataFilePath("t.csv"))
    assert(s.next('\n')=="Name,Value,Flag")
    s.close
  }
  test("low-level schema scanning") {
    val s = new ScannerLib(dataFilePath("t.csv"))
    val v = s.next('\n').split(',').toVector
    assert(v==Vector("Name","Value","Flag"))
    s.close
  }
  test("low-level record scanning knowing schema") {
    val s = new ScannerLib(dataFilePath("t.csv"))
    val fields = Vector("Name","Value","Flag")
    val last = fields.last
    val v = fields.map{x => s.next(if (x==last) '\n' else ',')}
    assert(v==Vector("Name","Value","Flag"))
    s.close
  }
}

trait ScannerBase extends Base {
  implicit class ScannerOps(s: Rep[Scanner]) {
    def next(implicit pos: SourceContext) = scannerNext(s)
    def hasNext(implicit pos: SourceContext) = scannerHasNext(s)
  }
  def newScanner(fn: Rep[String], d: Rep[Char])(implicit pos: SourceContext): Rep[Scanner]
  def scannerNext(s: Rep[Scanner])(implicit pos: SourceContext): Rep[String]
  def scannerHasNext(s: Rep[Scanner])(implicit pos: SourceContext): Rep[Boolean]
}

trait ScannerExp extends ScannerBase with EffectExp {
  case class ScannerNew(fn: Exp[String], d: Exp[Char]) extends Def[Scanner]
  case class ScannerNext(s: Exp[Scanner]) extends Def[String]
  case class ScannerHasNext(s: Exp[Scanner]) extends Def[Boolean]

  override def newScanner(fn: Rep[String], d: Rep[Char])(implicit pos: SourceContext): Rep[Scanner] =
    reflectMutable(ScannerNew(fn, d))
  override def scannerNext(s: Rep[Scanner])(implicit pos: SourceContext): Rep[String] =
    reflectWrite(s)(ScannerNext(s))
  override def scannerHasNext(s: Rep[Scanner])(implicit pos: SourceContext): Rep[Boolean] =
    reflectWrite(s)(ScannerHasNext(s))

  override def mirror[A:Manifest](e: Def[A], f: Transformer)(implicit pos: SourceContext): Exp[A] = (e match {
    case Reflect(e@ScannerNew(fn, d), u, es) => reflectMirrored(Reflect(ScannerNew(f(fn), f(d)), mapOver(f,u), f(es)))(mtype(manifest[A]), pos)
    case Reflect(ScannerNext(s), u, es) => reflectMirrored(Reflect(ScannerNext(f(s)), mapOver(f,u), f(es)))(mtype(manifest[A]), pos)
    case Reflect(ScannerHasNext(s), u, es) => reflectMirrored(Reflect(ScannerHasNext(f(s)), mapOver(f,u), f(es)))(mtype(manifest[A]), pos)
    case _ => super.mirror(e,f)
  }).asInstanceOf[Exp[A]]


}


trait ScalaGenScanner extends ScalaGenEffect {
  val IR: ScannerExp
  import IR._

  override def emitNode(sym: Sym[Any], rhs: Def[Any]) = rhs match {
    case ScannerNew(fn, d) => emitValDef(sym, src"new scala.lms.tutorial.Scanner($fn, $d)")
    case ScannerNext(s) => emitValDef(sym, src"$s.next")
    case ScannerHasNext(s) => emitValDef(sym, src"$s.hasNext")
    case _ => super.emitNode(sym, rhs)
  }
}
