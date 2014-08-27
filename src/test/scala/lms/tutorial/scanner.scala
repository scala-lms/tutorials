package scala.lms.tutorial

import scala.virtualization.lms.common._
import scala.reflect.SourceContext

import java.io.FileReader
import java.io.BufferedReader

class Scanner(filename: String) {
  val fieldDelimiter = ","

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

trait ScannerBase extends Base {
  implicit class ScannerOps(s: Rep[Scanner]) {
    def next(implicit pos: SourceContext) = scannerNext(s)
    def hasNext(implicit pos: SourceContext) = scannerHasNext(s)
  }
  def newScanner(fn: Rep[String])(implicit pos: SourceContext): Rep[Scanner]
  def scannerNext(s: Rep[Scanner])(implicit pos: SourceContext): Rep[String]
  def scannerHasNext(s: Rep[Scanner])(implicit pos: SourceContext): Rep[Boolean]
}

trait ScannerExp extends ScannerBase with EffectExp {
  case class ScannerNew(fn: Exp[String]) extends Def[Scanner]
  case class ScannerNext(s: Exp[Scanner]) extends Def[String]
  case class ScannerHasNext(s: Exp[Scanner]) extends Def[Boolean]

  override def newScanner(fn: Rep[String])(implicit pos: SourceContext): Rep[Scanner] =
    reflectMutable(ScannerNew(fn))
  override def scannerNext(s: Rep[Scanner])(implicit pos: SourceContext): Rep[String] =
    reflectWrite(s)(ScannerNext(s))
  override def scannerHasNext(s: Rep[Scanner])(implicit pos: SourceContext): Rep[Boolean] =
    reflectWrite(s)(ScannerHasNext(s))

  override def mirror[A:Manifest](e: Def[A], f: Transformer)(implicit pos: SourceContext): Exp[A] = (e match {
    case Reflect(e@ScannerNew(fn), u, es) => reflectMirrored(Reflect(ScannerNew(f(fn)), mapOver(f,u), f(es)))(mtype(manifest[A]), pos)
    case Reflect(ScannerNext(s), u, es) => reflectMirrored(Reflect(ScannerNext(f(s)), mapOver(f,u), f(es)))(mtype(manifest[A]), pos)
    case Reflect(ScannerHasNext(s), u, es) => reflectMirrored(Reflect(ScannerHasNext(f(s)), mapOver(f,u), f(es)))(mtype(manifest[A]), pos)
    case _ => super.mirror(e,f)
  }).asInstanceOf[Exp[A]]


}

trait ScalaGenScanner extends ScalaGenEffect {
  val IR: ScannerExp
  import IR._

  override def emitNode(sym: Sym[Any], rhs: Def[Any]) = rhs match {
    case ScannerNew(fn) => emitValDef(sym, src"new scala.lms.tutorial.Scanner($fn)")
    case ScannerNext(s) => emitValDef(sym, src"$s.next")
    case ScannerHasNext(s) => emitValDef(sym, src"$s.hasNext")
    case _ => super.emitNode(sym, rhs)
  }
}
