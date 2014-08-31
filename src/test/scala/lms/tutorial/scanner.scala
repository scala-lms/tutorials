package scala.lms.tutorial

import scala.virtualization.lms.common._
import scala.reflect.SourceContext

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

trait CGenScanner extends CGenEffect {
  val IR: ScannerExp
  import IR._

  // FXIME: C impl
  override def remap[A](m: Manifest[A]): String = m.toString match {
    case "scala.lms.tutorial.Scanner" => "void*/*TODO:Scanner*/"
    case _ => super.remap(m)
  }
  override def emitNode(sym: Sym[Any], rhs: Def[Any]) = rhs match {
    case ScannerNew(fn, d) => emitValDef(sym, src"NULL/*TODO:ScannerNew*/")
    case ScannerNext(s) => emitValDef(sym, src"NULL/*TODO:ScannerNext*/")
    case ScannerHasNext(s) => emitValDef(sym, src"NULL/*TODO:ScannerHasNext*/")
    case _ => super.emitNode(sym, rhs)
  }
}
