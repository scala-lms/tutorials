package scala.virtualization.lms.stub.common

import scala.virtualization.lms.util.OverloadHack
import org.scala_lang.virtualized.SourceContext
import org.scala_lang.virtualized.EmbeddedControls

import lms.core._
import Backend._

// - XXX hook for optimization is missing

class Unknown

object Adapter extends FrontEnd {
  val sc = new lms.util.ScalaCompile {}
  // sc.dumpGeneratedCode = true


  // def mkClassName(name: String) = {
  //   // mangle class name
  //   (name).replace("-","_")
  // }

  def emitScala(name: String)(m1:Manifest[_],m2:Manifest[_])(prog: Exp => Exp) = {
    emitCommon(name, "scala")(m1, m2)(prog)
  }

  def emitC(name: String)(m1:Manifest[_],m2:Manifest[_])(prog: Exp => Exp) = {
    emitCommon(name, "c")(m1, m2)(prog)
  }

  var typeMap: scala.collection.mutable.HashMap[lms.core.Backend.Exp, Manifest[_]] = _


  def emitCommon(name: String, target: String, verbose: Boolean = false, alt: Boolean = false, eff: Boolean = false)(m1:Manifest[_],m2:Manifest[_])(prog: Exp => Exp) = {
    // test(name) {
      // lms.util.checkOut(name, "scala", {
        var g = program(x => INT(prog(x.x)))

        val extra =  if (verbose) utils.captureOut {
          println("// Raw:")
          g.nodes.foreach(println)

          println("// Generic Codegen:")
          (new CodeGen)(g)

          println("// Scala Codegen:")
          (new ScalaCodeGen)(g)

          println("// Compact Scala Codegen:")
          (new ExtendedScalaCodeGen)(g)
        } else ""

        def emitSource() = {
          val cg: ExtendedCodeGen = target match {
            case "scala" => new ExtendedScalaCodeGen
            case "c"     => new ExtendedCCodeGen
          }
          if (!verbose) cg.doRename = true
          if (eff)      cg.doPrintEffects = true

          val arg = cg.quote(g.block.in.head)
          val efs = cg.quoteEff(g.block.ein)
          var src = utils.captureOut(cg(g))

          if (!verbose) {
            // remove "()" on a single line
            src = src.replaceAll("\\n *\\(\\) *\\n","\n")

            // XXX hack for types
            val typeMap1 = typeMap.map(p => (p._1,p._2.asInstanceOf[Manifest[Any]])).toMap
            val reverseMap = cg.rename.map(p => (p._2,p._1)).toMap

            // remove unused val x1 = ... (and add types for C)
            val names = cg.rename.map(p => p._2).toSet
            for (n <- names) {
              val removed = src.replace(s"val $n = ","")
              if (!removed.replaceFirst("\\b"+n+"\\b","!!!!").contains("!!!!")) { // shouldn't match x10 when looking for x1
                src = removed
              }
              if (target == "c") { // XXX HACK: add types for C code!!!
                val orig = reverseMap(n)
                // Sysstem.out.println("type map: "+typeMap1)
                val tpe = typeMap1.getOrElse(orig,manifest[Unknown])
                val tpe1 = cg.remap(tpe)
                src = src.replace(s"val $n = ",s"$tpe1 $n = ")
                src = src.replace(s"var $n = ",s"$tpe1 $n = ")
              }
            }

            // remove "xNN" on a single line (may overlap, so repeat a few times)
            src = src.replaceAll("\\n *x[0-9]+;? *\\n","\n")
            src = src.replaceAll("\\n *x[0-9]+;? *\\n","\n")
            src = src.replaceAll("\\n *x[0-9]+;? *\\n","\n")

          }

          target match {
            case "scala" => 
              val (ms1, ms2) = (cg.remap(m1), cg.remap(m2))
              val className = name
              s"""
              /*****************************************
              Emitting Generated Code
              *******************************************/
              class $className extends ($ms1 => $ms2) {
                def apply($arg: $ms1): $ms2$efs = {\n $src\n }
              }
              /*****************************************
              End of Generated Code
              *******************************************/
              """
            case "c"     => 
              val (ms1, ms2) = (cg.remap(m1), cg.remap(m2))
              val functionName = name
              s"""
              #include <fcntl.h>
              #include <errno.h>
              #include <err.h>
              #include <sys/mman.h>
              #include <sys/stat.h>
              #include <stdio.h>
              #include <stdint.h>
              #include <unistd.h>
              #ifndef MAP_FILE
              #define MAP_FILE MAP_SHARED
              #endif
              int fsize(int fd) {
                struct stat stat;
                int res = fstat(fd,&stat);
                return stat.st_size;
              }
              int printll(char* s) {
                while (*s != '\\n' && *s != ',' && *s != '\\t') {
                  putchar(*s++);
                }
                return 0;
              }
              long hash(char *str0, int len)
              {
                unsigned char* str = (unsigned char*)str0;
                unsigned long hash = 5381;
                int c;

                while ((c = *str++) && len--)
                  hash = ((hash << 5) + hash) + c; /* hash * 33 + c */

                return hash;
              }
              void Snippet(char*);
              int main(int argc, char *argv[])
              {
                if (argc != 2) {
                  printf("usage: query <filename>\\n");
                  return 0;
                }
                Snippet(argv[1]);
                return 0;
              }
              /*****************************************
              Emitting C Generated Code
              *******************************************/
              #include <stdio.h>
              #include <stdlib.h>
              #include <string.h>
              #include <stdbool.h>
              $ms2 $functionName($ms1 $arg) {
                $src
              }
              /*****************************************
              End of C Generated Code
              *******************************************/
              """
          }
        }

        extra + emitSource()
    }

}



abstract class ExtendedCodeGen extends CompactScalaCodeGen {

  override def quote(x: Def) = x match {
    case Const(s: String) => "\""+s.replace("\"", "\\\"").replace("\n","\\n").replace("\t","\\t")+"\"" // TODO: more escapes?
    case Const(x @ '\n') if x.isInstanceOf[Char] => "'\\n'"
    case Const(x @ '\t') if x.isInstanceOf[Char] => "'\\t'"
    case Const(x) if x.isInstanceOf[Char] && x == 0 => "'\\0'"
    case Const(x) if x.isInstanceOf[Long] => x.toString + "L"
    case _ => super.quote(x)
  }

  // XXX proper operator precedence
  def shallow1(n: Def): String = n match {
    case InlineSym(n) if n.op != "var_get" => s"(${shallow(n)})"
    case _ => shallow(n)
  }

  def remap(m: Manifest[_]): String
  def nameMap: Map[String, String]

  override def shallow(n: Node): String = n match {
    case n @ Node(s,op,args,_) if nameMap contains op => 
      shallow(n.copy(op = nameMap(n.op)))
    case n @ Node(s,"<",List(a,b),_) => 
      s"${shallow(a)} < ${shallow(b)}"
    case n @ Node(s,"&",List(a,b),_) => 
      s"${shallow1(a)} & ${shallow1(b)}"
    case n @ Node(s,"Boolean.!",List(a),_) => 
      s"!${shallow1(a)}"
    case n @ Node(s,op,args,_) if op contains "[ ]" => // unchecked
      var s = op
      for (a <- args)
        s = s.replaceFirst("\\[ \\]",shallow(a))
      s
    case n @ Node(s,op,args,_) if op.contains('.') && !op.contains(' ') => // method call
      val (recv::args1) = args
      if (args1.length > 0)
        s"${shallow1(recv)}.${op.drop(op.lastIndexOf('.')+1)}(${args1.map(shallow).mkString(",")})"
      else
        s"${shallow1(recv)}.${op.drop(op.lastIndexOf('.')+1)}"
    case n @ Node(s,"?",List(c,a,b:Block),_) if b.isPure && b.res == Const(false) => 
      s"${shallow(c)} && ${shallow(a)}"
    case n => 
      super.shallow(n)
  }
}

class ExtendedScalaCodeGen extends ExtendedCodeGen {
  def remap(m: Manifest[_]): String = m.toString
  val nameMap = Map(
    "ScannerNew"     -> "new scala.lms.tutorial.Scanner",
    "ScannerHasNext" -> "Scanner.hasNext",
    "ScannerNext"    -> "Scanner.next",
    "ScannerClose"   -> "Scanner.close",
    "ObjHashCode"    -> "Object.hashCode",
  )
}

class ExtendedCCodeGen extends ExtendedCodeGen {
  def remap(m: Manifest[_]): String = m.toString match {
    case "Unit" => "void"
    case "Int" => "int"
    case "java.lang.String" => "char*"
    case "Array[Char]" => "char*"
    case s => s
  }
  val nameMap = Map(
    "ScannerNew"     -> "new scala.lms.tutorial.Scanner",
    "ScannerHasNext" -> "Scanner.hasNext",
    "ScannerNext"    -> "Scanner.next",
    "ScannerClose"   -> "Scanner.close",
    "ObjHashCode"    -> "Object.hashCode"
  )
  override def emitValDef(s: Sym, rhs: String): Unit = {
    emit(s"val ${quote(s)} = " + rhs + ";") 
  }
  override def shallow(n: Node): String = n match {
    case Node(s,"Char.toInt",List(a),_) =>
      s"(int)${shallow1(a)}"
    case Node(s,"array_get",List(a,i),_) =>
      s"${shallow1(a)}[${shallow(i)}]"
    // case Node(s,"var_get",List(a),_) =>
      // quote(a)+s"/*${quote(s)}*/"
    case n @ Node(s,"P",List(x),_) => 
      s"printf(${"\"%s\""}, ${shallow(x)})"
    case n => 
      super.shallow(n)
  }
  override def traverse(n: Node): Unit = n match {
    // case n @ Node(s,"P",_,_) => // Unit result
    //   emit(shallow(n))
    // case n @ Node(s,"W",_,_) => // Unit result
    //   emit(shallow(n))
    case n @ Node(s,"var_new",List(x),_) => 
      emit(s"var ${quote(s)} = ${shallow(x)};")
    case n @ Node(s,"var_set",List(x,y),_) => 
      emit(s"${quote(x)} = ${shallow(y)};")
    case n @ Node(s,"array_new",List(x),_) => 
      emit(s"val ${quote(s)} = new Array[Int](${shallow(x)});")
    case n @ Node(s,"array_set",List(x,i,y),_) => 
      emit(s"${shallow(x)}(${shallow(i)}) = ${shallow(y)};")
    case n @ Node(s,_,_,_) => 
      // emit(s"val ${quote(s)} = " + shallow(n)) 
      emitValDef(s, shallow(n))
  }
}


trait Base extends EmbeddedControls with OverloadHack { 
  type Rep[+T] = Exp[T];
  abstract class Exp[+T]
  abstract class Def[+T]
  abstract class Var[T]
  abstract class Block[T]

  val typeMap = new scala.collection.mutable.HashMap[lms.core.Backend.Exp, Manifest[_]]()

  case class Wrap[A:Manifest](x: lms.core.Backend.Exp) extends Exp[A] {
    typeMap(x) = manifest[A]
  }
  def Unwrap(x: Exp[Any]) = x match { 
    case Wrap(x) => x 
    case Const(x) => Backend.Const(x)
  }

  case class WrapV[A:Manifest](x: lms.core.Backend.Exp) extends Var[A] {
    typeMap(x) = manifest[A] // could include Var type in manifest
  }
  def UnwrapV[T](x: Var[T]) = x match { case WrapV(x) => x }

  case class Const[T](x: T) extends Exp[T]
  case class Sym[T](x: Int) extends Exp[T]

  implicit def unit[T](x: T): Rep[T] = Wrap(Backend.Const(x))
  implicit def toAtom[T](x: Def[T]): Exp[T] = {
    val p = x.asInstanceOf[Product]
    val xs = p.productIterator.map(_.asInstanceOf[Exp[Any]]).map(Unwrap).toSeq
    Wrap(Adapter.g.reflect(p.productPrefix, xs:_*))
  }

  def staticData[T](x: T): Rep[T] = ???

  def reflectEffect[T](x: Def[T]): Exp[T] = ???
  def reflectMutable[T](x: Def[T]): Exp[T] = {
    val p = x.asInstanceOf[Product]
    val xs = p.productIterator.map(_.asInstanceOf[Exp[Any]]).map(Unwrap).toSeq
    Wrap(Adapter.g.reflectEffect(p.productPrefix, xs:_*)(Adapter.STORE))
  }
  def reflectWrite[T](w: Rep[Any])(x: Def[T]): Exp[T] = {
    val p = x.asInstanceOf[Product]
    val xs = p.productIterator.map(_.asInstanceOf[Exp[Any]]).map(Unwrap).toSeq
    Wrap(Adapter.g.reflectEffect(p.productPrefix, xs:_*)(Unwrap(w)))
  }

  def emitValDef(sym: Sym[Any], rhs: String): Unit = ???

  def fun[A,B](f: Rep[A] => Rep[B]): Rep[A => B] = ???
  def doLambda[A,B](f: Rep[A] => Rep[B]): Rep[A => B] = ???
  implicit class FunOps[A,B](f: Rep[A => B]) {
    def apply(x: Rep[A]): Rep[B] = ???
  }

  // def compile[A,B](f: Rep[A] => Rep[B]): A=>B = ???
  def compile[A:Manifest,B:Manifest](f: Rep[A] => Rep[B]): A=>B = {
    val src = Adapter.emitScala("Snippet")(manifest[A],manifest[B])(x => Unwrap(f(Wrap(x))))
    val fc = Adapter.sc.compile[A,B]("Snippet", src)
    fc
  }

  class SeqOpsCls[T](x: Rep[Seq[Char]])

  // XXX HACK for generic type!
  def NewArray[T:Manifest](x: Rep[Int]): Rep[Array[T]] = Wrap[Array[T]](Adapter.g.reflectEffect("new Array["+manifest[T]+"]", Unwrap(x))(Adapter.STORE))
  implicit class ArrayOps[A](x: Rep[Array[A]]) {
    def apply(i: Rep[Int]): Rep[A] = Wrap(Adapter.g.reflectEffect("array_get", Unwrap(x), Unwrap(i))(Unwrap(x)))
    def update(i: Rep[Int], y: Rep[A]): Rep[Unit] = Wrap(Adapter.g.reflectEffect("array_set", Unwrap(x), Unwrap(i), Unwrap(y))(Unwrap(x)))
    def length: Rep[Int] = Wrap(Adapter.g.reflect("Array.length", Unwrap(x)))
  }


  implicit def readVar[T:Manifest](x: Var[T]): Rep[T] = Wrap(Adapter.g.reflectEffect("var_get", UnwrapV(x))(UnwrapV(x)))
  def var_new[T:Manifest](x: Rep[T]): Var[T] = WrapV(Adapter.g.reflectEffect("var_new", Unwrap(x))(Adapter.STORE))
  def __assign[T:Manifest](lhs: Var[T], rhs: Rep[T]): Unit = Wrap(Adapter.g.reflectEffect("var_set", UnwrapV(lhs), Unwrap(rhs))(UnwrapV(lhs)))
  def __assign[T:Manifest](lhs: Var[T], rhs: Var[T]): Unit = __assign(lhs,readVar(rhs))


  def numeric_plus[T:Numeric](lhs: Rep[T], rhs: Rep[T]): Rep[T] =
    Wrap((Adapter.INT(Unwrap(lhs)) + Adapter.INT(Unwrap(rhs))).x) // XXX: not distinguishing types here ...

  implicit class OpsInfixVarT[T:Manifest:Numeric](lhs: Var[T]) {
    def +=(rhs: T): Unit = __assign(lhs,numeric_plus(readVar(lhs),rhs))
  }


  implicit class StringOps(lhs: Rep[String]) {
    def charAt(i: Rep[Int]): Rep[Char] = Wrap(Adapter.g.reflect("String.charAt", Unwrap(lhs), Unwrap(i))) // XXX: may fail! effect?
    def apply(i: Rep[Int]): Rep[Char] = charAt(i)
    def length: Rep[Int] = Wrap(Adapter.g.reflect("String.length", Unwrap(lhs)))
    def toInt: Rep[Int] = Wrap(Adapter.g.reflect("String.toInt", Unwrap(lhs))) // XXX: may fail!
  }

  // NOTE(trans): it has to be called 'intWrapper' to shadow the standard Range constructor
  implicit class intWrapper(start: Int) {
    // Note that these are ambiguous - need to use type ascription here (e.g. 1 until 10 : Rep[Range])
    
    def until(end: Rep[Int])(implicit pos: SourceContext) = range_until(unit(start),end)
    def until(end: Int)(implicit pos: SourceContext): Rep[Range] = range_until(unit(start),unit(end))

    def until(end: Int)(implicit pos: SourceContext, o: Overloaded1): Range = new Range(start,end,1)
  }
  def range_until(start: Rep[Int], end: Rep[Int]): Rep[Range] = {
    Wrap(Adapter.g.reflect("range_until", Unwrap(start), Unwrap(end)))
  }

  implicit class RangeConstrOps(lhs: Rep[Int]) {
    def until(y: Rep[Int]): Rep[Range] = range_until(lhs,y)

    // unrelated
    def ToString: Rep[String] = 
      Wrap(Adapter.g.reflect("Object.toString", Unwrap(lhs)))
  }

  implicit class RangeOps(lhs: Rep[Range]) {
    def foreach(f: Rep[Int] => Rep[Unit]): Rep[Unit] = {

      // XXX TODO: it would be good to do this as lowering/codegen
      // (as done previously in LMS), but for now just construct
      // a while loop directly

      val Adapter.g.Def("range_until", List(x0:Backend.Exp,x1:Backend.Exp)) = Unwrap(lhs)

      // val b = Adapter.g.reify(i => Unwrap(f(Wrap(i))))
      // val f1 = Adapter.g.reflect("λ",b)
      // Wrap(Adapter.g.reflect("range_foreach", x0, x1, b))

      val i = Adapter.VAR(Adapter.INT(x0))
      Adapter.WHILE(i() !== Adapter.INT(x1)) {
        f(Wrap(i().x))
        i() = i() + 1
      }
    }
  }

  implicit def bool2boolOps(lhs: Boolean) = new BoolOps(lhs)
  implicit def var2boolOps(lhs: Var[Boolean]) = new BoolOps(lhs)
  implicit class BoolOps(lhs: Rep[Boolean]) {
    def unary_!(implicit pos: SourceContext): Rep[Boolean] = Wrap(Adapter.g.reflect("Boolean.!", Unwrap(lhs)))
    def &&(rhs: => Rep[Boolean]): Rep[Boolean] = ???
    def ||(rhs: => Rep[Boolean]): Rep[Boolean] = ???
  }


  def println(x: Rep[Any]): Unit = 
    Adapter.g.reflectEffect("P",Unwrap(x))(Adapter.CTRL)

  def printf(f: String, x: Rep[Any]*): Unit = {
    Adapter.g.reflectEffect("printf",Backend.Const(f)::x.map(Unwrap).toList:_*)(Adapter.CTRL)
  }


  def __ifThenElse[T:Manifest](c: Rep[Boolean], a: => Rep[T], b: => Rep[T])(implicit pos: SourceContext): Rep[T] = {
      Wrap(Adapter.IF(Adapter.BOOL(Unwrap(c)))
                     (Adapter.INT(Unwrap(a)))
                     (Adapter.INT(Unwrap(b))).x)
  }
  def __whileDo(c: => Rep[Boolean], b: => Rep[Unit]): Rep[Unit] = {
      Adapter.WHILE(Adapter.BOOL(Unwrap(c)))(b)
  }

  def unchecked[T:Manifest](xs: Any*): Rep[T] = {
    val strings = xs collect { case s: String => s } mkString "[ ]"
    val args = xs collect { case e: Exp[Any] => e }
    Wrap(Adapter.g.reflectEffect(strings, args.map(Unwrap):_*)(Adapter.CTRL))
  }
  def uncheckedPure[T:Manifest](xs: Any*): Rep[T] = {
    val strings = xs collect { case s: String => s } mkString "[ ]"
    val args = xs collect { case e: Exp[Any] => e }
    Wrap(Adapter.g.reflect(strings, args.map(Unwrap):_*))
  }

  case class GenerateComment(l: String) extends Def[Unit]
  case class Comment[A:Manifest](l: String, verbose: Boolean, b: Block[A]) extends Def[A]
  def generate_comment(l: String): Rep[Unit] = {
    Wrap(Adapter.g.reflectEffect("generate-comment", Backend.Const(l))(Adapter.CTRL))
  }
  def comment[A:Manifest](l: String, verbose: Boolean = true)(b: => Rep[A]): Rep[A] = {
    val g = Adapter.g
    val bb = g.reify(Unwrap(b))
    if (g.isPure(bb))
      Wrap(g.reflect("comment",Backend.Const(l),Backend.Const(verbose),bb))
    else 
      Wrap(g.reflectEffect("comment",Backend.Const(l),Backend.Const(verbose),bb)(g.getEffKeys(bb):_*))
  }


}

trait Compile
trait CompileScala

trait BaseExp
trait ScalaGenBase {
  val IR: Base
  import IR._
  implicit class CodegenHelper(sc: StringContext) {
    def src(args: Any*): String = ???
  }
  def remap[A](m: Manifest[A]): String = ???
  def quote(x: Exp[Any]) : String = ???
  def emitNode(sym: Sym[Any], rhs: Def[Any]): Unit = ???
  def emitSource[A : Manifest, B : Manifest](f: Rep[A]=>Rep[B], className: String, stream: java.io.PrintWriter): List[(Sym[Any], Any)] = {
    Adapter.typeMap = typeMap // XXX
    val src = Adapter.emitScala(className)(manifest[A],manifest[B])(x => Unwrap(f(Wrap(x))))
    stream.println(src)
    Nil
  }
  def emitSource[A : Manifest](args: List[Sym[_]], body: Block[A], className: String, stream: java.io.PrintWriter): List[(Sym[Any], Any)] = ???
}
trait CGenBase {
  val IR: Base
  import IR._
  def remap[A](m: Manifest[A]): String = ???
  def quote(x: Exp[Any]) : String = ???
  def emitNode(sym: Sym[Any], rhs: Def[Any]): Unit = ???
  def emitSource[A : Manifest, B : Manifest](f: Rep[A]=>Rep[B], className: String, stream: java.io.PrintWriter): List[(Sym[Any], Any)] = {
    Adapter.typeMap = typeMap // XXX
    val src = Adapter.emitC(className)(manifest[A],manifest[B])(x => Unwrap(f(Wrap(x))))
    stream.println(src)
    Nil
  }
  def emitSource[A : Manifest](args: List[Sym[_]], body: Block[A], className: String, stream: java.io.PrintWriter): List[(Sym[Any], Any)] = ???
}

// trait PrimitiveOps 
trait NumericOps 
trait BooleanOps 
trait LiftString 
// trait LiftPrimitives 
trait LiftNumeric 
trait LiftBoolean 
trait IfThenElse 
// trait Equal 
trait RangeOps 
// trait OrderingOps 
trait MiscOps 
trait ArrayOps 
trait StringOps 
trait SeqOps 
trait Functions 
trait While 
trait StaticData 
trait Variables 
// trait LiftVariables 
trait ObjectOps 
trait UtilOps
trait UncheckedOps

trait PrimitiveOpsExpOpt 
trait NumericOpsExpOpt 
trait BooleanOpsExp 
trait IfThenElseExpOpt 
trait EqualExpBridgeOpt 
trait RangeOpsExp 
trait OrderingOpsExp 
trait MiscOpsExp 
trait EffectExp 
trait ArrayOpsExpOpt 
trait StringOpsExp 
trait SeqOpsExp 
trait FunctionsRecursiveExp 
trait WhileExp 
trait StaticDataExp 
trait VariablesExpOpt 
trait ObjectOpsExpOpt 
trait UtilOpsExp
trait UncheckedOpsExp

trait ScalaGenNumericOps extends ScalaGenBase
trait ScalaGenPrimitiveOps extends ScalaGenBase
trait ScalaGenBooleanOps extends ScalaGenBase
trait ScalaGenIfThenElse extends ScalaGenBase
trait ScalaGenEqual extends ScalaGenBase
trait ScalaGenRangeOps extends ScalaGenBase
trait ScalaGenOrderingOps extends ScalaGenBase
trait ScalaGenMiscOps extends ScalaGenBase
trait ScalaGenArrayOps extends ScalaGenBase
trait ScalaGenStringOps extends ScalaGenBase
trait ScalaGenSeqOps extends ScalaGenBase
trait ScalaGenFunctions extends ScalaGenBase
trait ScalaGenWhile extends ScalaGenBase
trait ScalaGenStaticData extends ScalaGenBase
trait ScalaGenVariables extends ScalaGenBase
trait ScalaGenObjectOps extends ScalaGenBase
trait ScalaGenUtilOps extends ScalaGenBase
trait ScalaGenUncheckedOps extends ScalaGenBase
trait ScalaGenEffect extends ScalaGenBase

trait CGenNumericOps
trait CGenPrimitiveOps
trait CGenBooleanOps 
trait CGenIfThenElse
trait CGenEqual
trait CGenRangeOps 
trait CGenOrderingOps
trait CGenMiscOps
trait CGenArrayOps 
trait CGenStringOps
trait CGenSeqOps
trait CGenFunctions 
trait CGenWhile
trait CGenStaticData
trait CGenVariables
trait CGenObjectOps
trait CGenUtilOps
trait CGenUncheckedOps


trait LiftVariables extends Base {
  def __newVar[T:Manifest](init: T)(implicit pos: SourceContext) = var_new(unit(init))
  def __newVar[T](init: Rep[T])(implicit o: Overloaded1, mT: Manifest[T], pos: SourceContext) = var_new(init)
  def __newVar[T](init: Var[T])(implicit o: Overloaded2, mT: Manifest[T], pos: SourceContext) = var_new(init)
}


trait Equal extends Base {
  def infix_==[A,B](a: Rep[A], b: Rep[B])(implicit o: Overloaded1, mA: Manifest[A], mB: Manifest[B], pos: SourceContext) : Rep[Boolean] = equals(a,b)
  def infix_==[A,B](a: Rep[A], b: Var[B])(implicit o: Overloaded2, mA: Manifest[A], mB: Manifest[B], pos: SourceContext) : Rep[Boolean] = equals(a, b)
  def infix_==[A,B](a: Var[A], b: Rep[B])(implicit o: Overloaded3, mA: Manifest[A], mB: Manifest[B], pos: SourceContext) : Rep[Boolean] = equals(a, b)
  def infix_==[A,B](a: Rep[A], b: B)(implicit o: Overloaded4, mA: Manifest[A], mB: Manifest[B], pos: SourceContext): Rep[Boolean] = equals(a, unit(b))
  def infix_==[A,B](a: A, b: Rep[B])(implicit o: Overloaded5, mA: Manifest[A], mB: Manifest[B], pos: SourceContext): Rep[Boolean] = equals(unit(a), b)
  def infix_==[A,B](a: Var[A], b: B)(implicit o: Overloaded6, mA: Manifest[A], mB: Manifest[B], pos: SourceContext): Rep[Boolean] = equals(a, unit(b))
  def infix_==[A,B](a: A, b: Var[B])(implicit o: Overloaded7, mA: Manifest[A], mB: Manifest[B], pos: SourceContext): Rep[Boolean] = equals(unit(a), b)
  def infix_==[A,B](a: Var[A], b: Var[B])(implicit o: Overloaded8, mA: Manifest[A], mB: Manifest[B], pos: SourceContext) : Rep[Boolean] = equals(a,b)

  def infix_!=[A,B](a: Rep[A], b: Rep[B])(implicit o: Overloaded1, mA: Manifest[A], mB: Manifest[B], pos: SourceContext) : Rep[Boolean] = notequals(a,b)
  def infix_!=[A,B](a: Rep[A], b: Var[B])(implicit o: Overloaded2, mA: Manifest[A], mB: Manifest[B], pos: SourceContext) : Rep[Boolean] = notequals(a, b)
  def infix_!=[A,B](a: Var[A], b: Rep[B])(implicit o: Overloaded3, mA: Manifest[A], mB: Manifest[B], pos: SourceContext) : Rep[Boolean] = notequals(a, b)
  def infix_!=[A,B](a: Rep[A], b: B)(implicit o: Overloaded4, mA: Manifest[A], mB: Manifest[B], pos: SourceContext) : Rep[Boolean] = notequals(a, unit(b))
  def infix_!=[A,B](a: A, b: Rep[B])(implicit o: Overloaded5, mA: Manifest[A], mB: Manifest[B], pos: SourceContext) : Rep[Boolean] = notequals(unit(a), b)
  def infix_!=[A,B](a: Var[A], b: B)(implicit o: Overloaded6, mA: Manifest[A], mB: Manifest[B], pos: SourceContext) : Rep[Boolean] = notequals(a, unit(b))
  def infix_!=[A,B](a: A, b: Var[B])(implicit o: Overloaded7, mA: Manifest[A], mB: Manifest[B], pos: SourceContext) : Rep[Boolean] = notequals(unit(a), b)
  def infix_!=[A,B](a: Var[A], b: Var[B])(implicit o: Overloaded8, mA: Manifest[A], mB: Manifest[B], pos: SourceContext) : Rep[Boolean] = notequals(a,b)  

  def equals[A:Manifest,B:Manifest](a: Rep[A], b: Rep[B])(implicit pos: SourceContext) : Rep[Boolean] = 
    Wrap(Adapter.g.reflect("==",Unwrap(a),Unwrap(b)))
  def notequals[A:Manifest,B:Manifest](a: Rep[A], b: Rep[B])(implicit pos: SourceContext) : Rep[Boolean] = 
    Wrap(Adapter.g.reflect("!=",Unwrap(a),Unwrap(b)))
}

trait OrderingOps extends Base with OverloadHack {
  // workaround for infix not working with implicits in PrimitiveOps
  implicit def orderingToOrderingOps[T:Ordering:Manifest](n: T) = new OrderingOpsCls(unit(n))
  implicit def repOrderingToOrderingOps[T:Ordering:Manifest](n: Rep[T]) = new OrderingOpsCls(n)
  implicit def varOrderingToOrderingOps[T:Ordering:Manifest](n: Var[T]) = new OrderingOpsCls(readVar(n))

  class OrderingOpsCls[T:Ordering:Manifest](lhs: Rep[T]){
    def <       (rhs: Rep[T])(implicit pos: SourceContext) = ordering_lt(lhs, rhs)
    def <=      (rhs: Rep[T])(implicit pos: SourceContext) = ordering_lteq(lhs, rhs)
    def >       (rhs: Rep[T])(implicit pos: SourceContext) = ordering_gt(lhs, rhs)
    def >=      (rhs: Rep[T])(implicit pos: SourceContext) = ordering_gteq(lhs, rhs)
    def equiv   (rhs: Rep[T])(implicit pos: SourceContext) = ordering_equiv(lhs, rhs)
    def max     (rhs: Rep[T])(implicit pos: SourceContext) = ordering_max(lhs, rhs)
    def min     (rhs: Rep[T])(implicit pos: SourceContext) = ordering_min(lhs, rhs)
    def compare (rhs: Rep[T])(implicit pos: SourceContext) = ordering_compare(lhs, rhs)

    def <       [B](rhs: B)(implicit c: B => Rep[T], pos: SourceContext) = ordering_lt(lhs, c(rhs))
    def <=      [B](rhs: B)(implicit c: B => Rep[T], pos: SourceContext) = ordering_lteq(lhs, c(rhs))
    def >       [B](rhs: B)(implicit c: B => Rep[T], pos: SourceContext) = ordering_gt(lhs, c(rhs))
    def >=      [B](rhs: B)(implicit c: B => Rep[T], pos: SourceContext) = ordering_gteq(lhs, c(rhs))
    def equiv   [B](rhs: B)(implicit c: B => Rep[T], pos: SourceContext) = ordering_equiv(lhs, c(rhs))
    def max     [B](rhs: B)(implicit c: B => Rep[T], pos: SourceContext) = ordering_max(lhs, c(rhs))
    def min     [B](rhs: B)(implicit c: B => Rep[T], pos: SourceContext) = ordering_min(lhs, c(rhs))
    def compare [B](rhs: B)(implicit c: B => Rep[T], pos: SourceContext) = ordering_compare(lhs, c(rhs))
  }

  def ordering_lt      [T:Ordering:Manifest](lhs: Rep[T], rhs: Rep[T])(implicit pos: SourceContext): Rep[Boolean] = Wrap[Boolean](Adapter.g.reflect("<", Unwrap(lhs), Unwrap(rhs)))
  def ordering_lteq    [T:Ordering:Manifest](lhs: Rep[T], rhs: Rep[T])(implicit pos: SourceContext): Rep[Boolean] = Wrap[Boolean](Adapter.g.reflect("<=", Unwrap(lhs), Unwrap(rhs)))
  def ordering_gt      [T:Ordering:Manifest](lhs: Rep[T], rhs: Rep[T])(implicit pos: SourceContext): Rep[Boolean] = Wrap[Boolean](Adapter.g.reflect(">", Unwrap(lhs), Unwrap(rhs)))
  def ordering_gteq    [T:Ordering:Manifest](lhs: Rep[T], rhs: Rep[T])(implicit pos: SourceContext): Rep[Boolean] = Wrap[Boolean](Adapter.g.reflect(">=", Unwrap(lhs), Unwrap(rhs)))
  def ordering_equiv   [T:Ordering:Manifest](lhs: Rep[T], rhs: Rep[T])(implicit pos: SourceContext): Rep[Boolean] = Wrap[Boolean](Adapter.g.reflect("==", Unwrap(lhs), Unwrap(rhs)))
  def ordering_max     [T:Ordering:Manifest](lhs: Rep[T], rhs: Rep[T])(implicit pos: SourceContext): Rep[T]       = Wrap[T]      (Adapter.g.reflect("max", Unwrap(lhs), Unwrap(rhs)))
  def ordering_min     [T:Ordering:Manifest](lhs: Rep[T], rhs: Rep[T])(implicit pos: SourceContext): Rep[T]       = Wrap[T]      (Adapter.g.reflect("min", Unwrap(lhs), Unwrap(rhs)))
  def ordering_compare [T:Ordering:Manifest](lhs: Rep[T], rhs: Rep[T])(implicit pos: SourceContext): Rep[Int]     = Wrap[Int]    (Adapter.g.reflect("compare", Unwrap(lhs), Unwrap(rhs)))
}


trait LiftPrimitives {
  this: PrimitiveOps =>

  implicit def intToRepInt(x: Int) = unit(x)
  implicit def floatToRepFloat(x: Float) = unit(x)
  implicit def doubleToRepDouble(x: Double) = unit(x)
  implicit def longToRepLong(x: Long) = unit(x)

  // precision-widening promotions
  implicit def chainIntToRepFloat[A:Manifest](x: A)(implicit c: A => Rep[Int]): Rep[Float] = repIntToRepFloat(c(x))
  implicit def chainFloatToRepDouble[A:Manifest](x: A)(implicit c: A => Rep[Float]): Rep[Double] = repFloatToRepDouble(c(x))
}

/**
 * This file is extremely boilerplate. In fact, most of the code here is copied from a
 * Forge-generated file. We need a static version since Delite (and other projects) depend
 * on it without using Forge.
 */
trait PrimitiveOps extends Base with OverloadHack {

  /**
   * Primitive conversions
   */
  implicit def repIntToRepDouble(x: Rep[Int]): Rep[Double] = x.toDouble
  implicit def repIntToRepFloat(x: Rep[Int]): Rep[Float] = x.toFloat
  implicit def repIntToRepLong(x: Rep[Int]): Rep[Long] = x.toLong
  implicit def repFloatToRepDouble(x: Rep[Float]): Rep[Double] = x.toDouble
  implicit def repLongToRepFloat(x: Rep[Long]): Rep[Float] = x.toFloat
  implicit def repLongToRepDouble(x: Rep[Long]): Rep[Double] = x.toDouble
  implicit def repCharToRepInt(x: Rep[Char]): Rep[Int] = x.toInt

  /**
   * Enumerate all combinations of primitive math.
   * Avoids certain fragile behavior, including compiler crashes and some erroneous or inaccessible type errors.
   */


  // -- BEGIN FORGE-GENERATED SECTION

  implicit def repToPrimitiveMathOpsDoubleOpsCls(x: Rep[Double])(implicit __pos: SourceContext) = new PrimitiveMathOpsDoubleOpsCls(x)(__pos)
  implicit def liftToPrimitiveMathOpsDoubleOpsCls(x: Double)(implicit __pos: SourceContext) = new PrimitiveMathOpsDoubleOpsCls(unit(x))(__pos)
  implicit def varToPrimitiveMathOpsDoubleOpsCls(x: Var[Double])(implicit __pos: SourceContext) = new PrimitiveMathOpsDoubleOpsCls(readVar(x))(__pos)

  class PrimitiveMathOpsDoubleOpsCls(val self: Rep[Double])(implicit __pos: SourceContext) {
    def +(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded9) = { double_plus(self, unit(rhs)) }
    def -(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded9) = { double_minus(self, unit(rhs)) }
    def *(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded9) = { double_times(self, unit(rhs)) }
    def /(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded10) = { double_divide(self, unit(rhs)) }
    def +(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded10) = { double_plus(self, rhs) }
    def -(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded10) = { double_minus(self, rhs) }
    def *(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded10) = { double_times(self, rhs) }
    def /(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded11) = { double_divide(self, rhs) }
    def +(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded11) = { double_plus(self, readVar(rhs)) }
    def -(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded11) = { double_minus(self, readVar(rhs)) }
    def *(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded11) = { double_times(self, readVar(rhs)) }
    def /(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded12) = { double_divide(self, readVar(rhs)) }
    def +(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded12) = { double_plus(self, unit(rhs.toDouble)) }
    def -(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded12) = { double_minus(self, unit(rhs.toDouble)) }
    def *(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded12) = { double_times(self, unit(rhs.toDouble)) }
    def /(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded13) = { double_divide(self, unit(rhs.toDouble)) }
    def +(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded13) = { double_plus(self, rhs.toDouble) }
    def -(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded13) = { double_minus(self, rhs.toDouble) }
    def *(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded13) = { double_times(self, rhs.toDouble) }
    def /(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded14) = { double_divide(self, rhs.toDouble) }
    def +(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded14) = { double_plus(self, readVar(rhs).toDouble) }
    def -(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded14) = { double_minus(self, readVar(rhs).toDouble) }
    def *(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded14) = { double_times(self, readVar(rhs).toDouble) }
    def /(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded15) = { double_divide(self, readVar(rhs).toDouble) }
    def +(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded15) = { double_plus(self, unit(rhs.toDouble)) }
    def -(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded15) = { double_minus(self, unit(rhs.toDouble)) }
    def *(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded15) = { double_times(self, unit(rhs.toDouble)) }
    def /(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded16) = { double_divide(self, unit(rhs.toDouble)) }
    def +(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded16) = { double_plus(self, rhs.toDouble) }
    def -(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded16) = { double_minus(self, rhs.toDouble) }
    def *(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded16) = { double_times(self, rhs.toDouble) }
    def /(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded17) = { double_divide(self, rhs.toDouble) }
    def +(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded17) = { double_plus(self, readVar(rhs).toDouble) }
    def -(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded17) = { double_minus(self, readVar(rhs).toDouble) }
    def *(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded17) = { double_times(self, readVar(rhs).toDouble) }
    def /(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded18) = { double_divide(self, readVar(rhs).toDouble) }
    def +(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded18) = { double_plus(self, unit(rhs.toDouble)) }
    def -(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded18) = { double_minus(self, unit(rhs.toDouble)) }
    def *(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded18) = { double_times(self, unit(rhs.toDouble)) }
    def /(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded19) = { double_divide(self, unit(rhs.toDouble)) }
    def +(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded19) = { double_plus(self, rhs.toDouble) }
    def -(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded19) = { double_minus(self, rhs.toDouble) }
    def *(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded19) = { double_times(self, rhs.toDouble) }
    def /(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded20) = { double_divide(self, rhs.toDouble) }
    def +(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded20) = { double_plus(self, readVar(rhs).toDouble) }
    def -(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded20) = { double_minus(self, readVar(rhs).toDouble) }
    def *(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded20) = { double_times(self, readVar(rhs).toDouble) }
    def /(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded21) = { double_divide(self, readVar(rhs).toDouble) }
  }

  implicit def repToPrimitiveMathOpsFloatOpsCls(x: Rep[Float])(implicit __pos: SourceContext) = new PrimitiveMathOpsFloatOpsCls(x)(__pos)
  implicit def liftToPrimitiveMathOpsFloatOpsCls(x: Float)(implicit __pos: SourceContext) = new PrimitiveMathOpsFloatOpsCls(unit(x))(__pos)
  implicit def varToPrimitiveMathOpsFloatOpsCls(x: Var[Float])(implicit __pos: SourceContext) = new PrimitiveMathOpsFloatOpsCls(readVar(x))(__pos)

  class PrimitiveMathOpsFloatOpsCls(val self: Rep[Float])(implicit __pos: SourceContext) {
    def +(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded41) = { double_plus(self.toDouble, unit(rhs)) }
    def -(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded41) = { double_minus(self.toDouble, unit(rhs)) }
    def *(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded41) = { double_times(self.toDouble, unit(rhs)) }
    def /(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded42) = { double_divide(self.toDouble, unit(rhs)) }
    def +(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded42) = { double_plus(self.toDouble, rhs) }
    def -(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded42) = { double_minus(self.toDouble, rhs) }
    def *(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded42) = { double_times(self.toDouble, rhs) }
    def /(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded43) = { double_divide(self.toDouble, rhs) }
    def +(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded43) = { double_plus(self.toDouble, readVar(rhs)) }
    def -(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded43) = { double_minus(self.toDouble, readVar(rhs)) }
    def *(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded43) = { double_times(self.toDouble, readVar(rhs)) }
    def /(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded44) = { double_divide(self.toDouble, readVar(rhs)) }
    def +(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded44) = { float_plus(self, unit(rhs)) }
    def -(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded44) = { float_minus(self, unit(rhs)) }
    def *(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded44) = { float_times(self, unit(rhs)) }
    def /(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded45) = { float_divide(self, unit(rhs)) }
    def +(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded45) = { float_plus(self, rhs) }
    def -(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded45) = { float_minus(self, rhs) }
    def *(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded45) = { float_times(self, rhs) }
    def /(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded46) = { float_divide(self, rhs) }
    def +(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded46) = { float_plus(self, readVar(rhs)) }
    def -(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded46) = { float_minus(self, readVar(rhs)) }
    def *(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded46) = { float_times(self, readVar(rhs)) }
    def /(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded47) = { float_divide(self, readVar(rhs)) }
    def +(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded47) = { float_plus(self, unit(rhs.toFloat)) }
    def -(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded47) = { float_minus(self, unit(rhs.toFloat)) }
    def *(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded47) = { float_times(self, unit(rhs.toFloat)) }
    def /(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded48) = { float_divide(self, unit(rhs.toFloat)) }
    def +(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded48) = { float_plus(self, rhs.toFloat) }
    def -(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded48) = { float_minus(self, rhs.toFloat) }
    def *(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded48) = { float_times(self, rhs.toFloat) }
    def /(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded49) = { float_divide(self, rhs.toFloat) }
    def +(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded49) = { float_plus(self, readVar(rhs).toFloat) }
    def -(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded49) = { float_minus(self, readVar(rhs).toFloat) }
    def *(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded49) = { float_times(self, readVar(rhs).toFloat) }
    def /(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded50) = { float_divide(self, readVar(rhs).toFloat) }
    def +(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded50) = { float_plus(self, unit(rhs.toFloat)) }
    def -(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded50) = { float_minus(self, unit(rhs.toFloat)) }
    def *(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded50) = { float_times(self, unit(rhs.toFloat)) }
    def /(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded51) = { float_divide(self, unit(rhs.toFloat)) }
    def +(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded51) = { float_plus(self, rhs.toFloat) }
    def -(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded51) = { float_minus(self, rhs.toFloat) }
    def *(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded51) = { float_times(self, rhs.toFloat) }
    def /(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded52) = { float_divide(self, rhs.toFloat) }
    def +(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded52) = { float_plus(self, readVar(rhs).toFloat) }
    def -(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded52) = { float_minus(self, readVar(rhs).toFloat) }
    def *(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded52) = { float_times(self, readVar(rhs).toFloat) }
    def /(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded53) = { float_divide(self, readVar(rhs).toFloat) }
  }

  implicit def repToPrimitiveMathOpsCharOpsCls(x: Rep[Char])(implicit __pos: SourceContext) = new PrimitiveMathOpsCharOpsCls(x)(__pos)
  implicit def liftToPrimitiveMathOpsCharOpsCls(x: Char)(implicit __pos: SourceContext) = new PrimitiveMathOpsCharOpsCls(unit(x))(__pos)
  implicit def varToPrimitiveMathOpsCharOpsCls(x: Var[Char])(implicit __pos: SourceContext) = new PrimitiveMathOpsCharOpsCls(readVar(x))(__pos)

  class PrimitiveMathOpsCharOpsCls(val self: Rep[Char])(implicit __pos: SourceContext) {
    //STUB
    def -(rhs: Char)(implicit __pos: SourceContext,__imp1: Overloaded73): Rep[Char] = char_minus(self, rhs)
  }

  def char_minus(lhs: Rep[Char], rhs: Rep[Char])(implicit pos: SourceContext): Rep[Char] = 
    Wrap((Adapter.INT(Unwrap(lhs)) - Adapter.INT(Unwrap(rhs))).x)    


  implicit def repToPrimitiveMathOpsIntOpsCls(x: Rep[Int])(implicit __pos: SourceContext) = new PrimitiveMathOpsIntOpsCls(x)(__pos)
  implicit def liftToPrimitiveMathOpsIntOpsCls(x: Int)(implicit __pos: SourceContext) = new PrimitiveMathOpsIntOpsCls(unit(x))(__pos)
  implicit def varToPrimitiveMathOpsIntOpsCls(x: Var[Int])(implicit __pos: SourceContext) = new PrimitiveMathOpsIntOpsCls(readVar(x))(__pos)

  class PrimitiveMathOpsIntOpsCls(val self: Rep[Int])(implicit __pos: SourceContext) {
    def +(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded73) = { double_plus(self.toDouble, unit(rhs)) }
    def -(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded73) = { double_minus(self.toDouble, unit(rhs)) }
    def *(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded73) = { double_times(self.toDouble, unit(rhs)) }
    def /(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded74) = { double_divide(self.toDouble, unit(rhs)) }
    def +(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded74) = { double_plus(self.toDouble, rhs) }
    def -(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded74) = { double_minus(self.toDouble, rhs) }
    def *(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded74) = { double_times(self.toDouble, rhs) }
    def /(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded75) = { double_divide(self.toDouble, rhs) }
    def +(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded75) = { double_plus(self.toDouble, readVar(rhs)) }
    def -(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded75) = { double_minus(self.toDouble, readVar(rhs)) }
    def *(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded75) = { double_times(self.toDouble, readVar(rhs)) }
    def /(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded76) = { double_divide(self.toDouble, readVar(rhs)) }
    def +(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded76) = { float_plus(self.toFloat, unit(rhs)) }
    def -(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded76) = { float_minus(self.toFloat, unit(rhs)) }
    def *(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded76) = { float_times(self.toFloat, unit(rhs)) }
    def /(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded77) = { float_divide(self.toFloat, unit(rhs)) }
    def +(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded77) = { float_plus(self.toFloat, rhs) }
    def -(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded77) = { float_minus(self.toFloat, rhs) }
    def *(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded77) = { float_times(self.toFloat, rhs) }
    def /(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded78) = { float_divide(self.toFloat, rhs) }
    def +(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded78) = { float_plus(self.toFloat, readVar(rhs)) }
    def -(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded78) = { float_minus(self.toFloat, readVar(rhs)) }
    def *(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded78) = { float_times(self.toFloat, readVar(rhs)) }
    def /(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded79) = { float_divide(self.toFloat, readVar(rhs)) }
    def +(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded79) = { int_plus(self, unit(rhs)) }
    def -(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded79) = { int_minus(self, unit(rhs)) }
    def *(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded79) = { int_times(self, unit(rhs)) }
    def /(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded80) = { int_divide(self, unit(rhs)) }
    def +(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded80) = { int_plus(self, rhs) }
    def -(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded80) = { int_minus(self, rhs) }
    def *(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded80) = { int_times(self, rhs) }
    def /(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded81) = { int_divide(self, rhs) }
    def +(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded81) = { int_plus(self, readVar(rhs)) }
    def -(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded81) = { int_minus(self, readVar(rhs)) }
    def *(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded81) = { int_times(self, readVar(rhs)) }
    def /(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded82) = { int_divide(self, readVar(rhs)) }
    def +(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded82) = { long_plus(self.toLong, unit(rhs)) }
    def -(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded82) = { long_minus(self.toLong, unit(rhs)) }
    def *(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded82) = { long_times(self.toLong, unit(rhs)) }
    def /(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded83) = { long_divide(self.toLong, unit(rhs)) }
    def +(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded83) = { long_plus(self.toLong, rhs) }
    def -(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded83) = { long_minus(self.toLong, rhs) }
    def *(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded83) = { long_times(self.toLong, rhs) }
    def /(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded84) = { long_divide(self.toLong, rhs) }
    def +(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded84) = { long_plus(self.toLong, readVar(rhs)) }
    def -(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded84) = { long_minus(self.toLong, readVar(rhs)) }
    def *(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded84) = { long_times(self.toLong, readVar(rhs)) }
    def /(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded85) = { long_divide(self.toLong, readVar(rhs)) }
  }

  implicit def repToPrimitiveMathOpsLongOpsCls(x: Rep[Long])(implicit __pos: SourceContext) = new PrimitiveMathOpsLongOpsCls(x)(__pos)
  implicit def liftToPrimitiveMathOpsLongOpsCls(x: Long)(implicit __pos: SourceContext) = new PrimitiveMathOpsLongOpsCls(unit(x))(__pos)
  implicit def varToPrimitiveMathOpsLongOpsCls(x: Var[Long])(implicit __pos: SourceContext) = new PrimitiveMathOpsLongOpsCls(readVar(x))(__pos)

  class PrimitiveMathOpsLongOpsCls(val self: Rep[Long])(implicit __pos: SourceContext) {
    def +(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded105) = { double_plus(self.toDouble, unit(rhs)) }
    def -(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded105) = { double_minus(self.toDouble, unit(rhs)) }
    def *(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded105) = { double_times(self.toDouble, unit(rhs)) }
    def /(rhs: Double)(implicit __pos: SourceContext,__imp1: Overloaded106) = { double_divide(self.toDouble, unit(rhs)) }
    def +(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded106) = { double_plus(self.toDouble, rhs) }
    def -(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded106) = { double_minus(self.toDouble, rhs) }
    def *(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded106) = { double_times(self.toDouble, rhs) }
    def /(rhs: Rep[Double])(implicit __pos: SourceContext,__imp1: Overloaded107) = { double_divide(self.toDouble, rhs) }
    def +(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded107) = { double_plus(self.toDouble, readVar(rhs)) }
    def -(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded107) = { double_minus(self.toDouble, readVar(rhs)) }
    def *(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded107) = { double_times(self.toDouble, readVar(rhs)) }
    def /(rhs: Var[Double])(implicit __pos: SourceContext,__imp1: Overloaded108) = { double_divide(self.toDouble, readVar(rhs)) }
    def +(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded108) = { float_plus(self.toFloat, unit(rhs)) }
    def -(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded108) = { float_minus(self.toFloat, unit(rhs)) }
    def *(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded108) = { float_times(self.toFloat, unit(rhs)) }
    def /(rhs: Float)(implicit __pos: SourceContext,__imp1: Overloaded109) = { float_divide(self.toFloat, unit(rhs)) }
    def +(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded109) = { float_plus(self.toFloat, rhs) }
    def -(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded109) = { float_minus(self.toFloat, rhs) }
    def *(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded109) = { float_times(self.toFloat, rhs) }
    def /(rhs: Rep[Float])(implicit __pos: SourceContext,__imp1: Overloaded110) = { float_divide(self.toFloat, rhs) }
    def +(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded110) = { float_plus(self.toFloat, readVar(rhs)) }
    def -(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded110) = { float_minus(self.toFloat, readVar(rhs)) }
    def *(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded110) = { float_times(self.toFloat, readVar(rhs)) }
    def /(rhs: Var[Float])(implicit __pos: SourceContext,__imp1: Overloaded111) = { float_divide(self.toFloat, readVar(rhs)) }
    def +(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded111) = { long_plus(self, unit(rhs.toLong)) }
    def -(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded111) = { long_minus(self, unit(rhs.toLong)) }
    def *(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded111) = { long_times(self, unit(rhs.toLong)) }
    def /(rhs: Int)(implicit __pos: SourceContext,__imp1: Overloaded112) = { long_divide(self, unit(rhs.toLong)) }
    def +(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded112) = { long_plus(self, rhs.toLong) }
    def -(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded112) = { long_minus(self, rhs.toLong) }
    def *(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded112) = { long_times(self, rhs.toLong) }
    def /(rhs: Rep[Int])(implicit __pos: SourceContext,__imp1: Overloaded113) = { long_divide(self, rhs.toLong) }
    def +(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded113) = { long_plus(self, readVar(rhs).toLong) }
    def -(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded113) = { long_minus(self, readVar(rhs).toLong) }
    def *(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded113) = { long_times(self, readVar(rhs).toLong) }
    def /(rhs: Var[Int])(implicit __pos: SourceContext,__imp1: Overloaded114) = { long_divide(self, readVar(rhs).toLong) }
    def +(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded114) = { long_plus(self, unit(rhs)) }
    def -(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded114) = { long_minus(self, unit(rhs)) }
    def *(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded114) = { long_times(self, unit(rhs)) }
    def /(rhs: Long)(implicit __pos: SourceContext,__imp1: Overloaded115) = { long_divide(self, unit(rhs)) }
    def +(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded115) = { long_plus(self, rhs) }
    def -(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded115) = { long_minus(self, rhs) }
    def *(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded115) = { long_times(self, rhs) }
    def /(rhs: Rep[Long])(implicit __pos: SourceContext,__imp1: Overloaded116) = { long_divide(self, rhs) }
    def +(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded116) = { long_plus(self, readVar(rhs)) }
    def -(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded116) = { long_minus(self, readVar(rhs)) }
    def *(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded116) = { long_times(self, readVar(rhs)) }
    def /(rhs: Var[Long])(implicit __pos: SourceContext,__imp1: Overloaded117) = { long_divide(self, readVar(rhs)) }
  }
  // -- END FORGE-GENERATED SECTION

  /**
   *  Double
   */

  object Double {
    def parseDouble(s:Rep[String]) = obj_double_parseDouble(s)
    def PositiveInfinity(implicit pos: SourceContext) = obj_double_positive_infinity
    def NegativeInfinity(implicit pos: SourceContext) = obj_double_negative_infinity
    def MinValue(implicit pos: SourceContext) = obj_double_min_value
    def MaxValue(implicit pos: SourceContext) = obj_double_max_value
  }

  implicit def doubleToDoubleOps(n: Double): DoubleOpsCls = new DoubleOpsCls(unit(n))
  implicit def repDoubleToDoubleOps(n: Rep[Double]): DoubleOpsCls = new DoubleOpsCls(n)
  implicit def varDoubleToDoubleOps(n: Var[Double]): DoubleOpsCls = new DoubleOpsCls(readVar(n))

  class DoubleOpsCls(self: Rep[Double]) {
    // def +(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded21) = { double_plus(self,unit(__arg1.toDouble)) }
    // def +(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded22) = { double_plus(self,unit(__arg1.toDouble)) }
    // def +(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded23) = { double_plus(self,unit(__arg1)) }
    // def +(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded24) = { double_plus(self,unit(__arg1.toDouble)) }
    // def +(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded30) = { double_plus(self,__arg1.toDouble) }
    // def +(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded31) = { double_plus(self,__arg1.toDouble) }
    // def +(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded32) = { double_plus(self,__arg1) }
    // def +(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded33) = { double_plus(self,__arg1.toDouble) }
    // def +(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded40) = { double_plus(self,readVar(__arg1).toDouble) }
    // def +(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded41) = { double_plus(self,readVar(__arg1).toDouble) }
    // def +(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded42) = { double_plus(self,readVar(__arg1)) }
    // def +(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded43) = { double_plus(self,readVar(__arg1).toDouble) }
    // def -(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded21) = { double_minus(self,unit(__arg1.toDouble)) }
    // def -(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded22) = { double_minus(self,unit(__arg1.toDouble)) }
    // def -(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded23) = { double_minus(self,unit(__arg1)) }
    // def -(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded24) = { double_minus(self,unit(__arg1.toDouble)) }
    // def -(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded30) = { double_minus(self,__arg1.toDouble) }
    // def -(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded31) = { double_minus(self,__arg1.toDouble) }
    // def -(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded32) = { double_minus(self,__arg1) }
    // def -(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded33) = { double_minus(self,__arg1.toDouble) }
    // def -(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded40) = { double_minus(self,readVar(__arg1).toDouble) }
    // def -(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded41) = { double_minus(self,readVar(__arg1).toDouble) }
    // def -(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded42) = { double_minus(self,readVar(__arg1)) }
    // def -(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded43) = { double_minus(self,readVar(__arg1).toDouble) }
    // def *(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded22) = { double_times(self,unit(__arg1.toDouble)) }
    // def *(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded23) = { double_times(self,unit(__arg1.toDouble)) }
    // def *(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded24) = { double_times(self,unit(__arg1)) }
    // def *(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded25) = { double_times(self,unit(__arg1.toDouble)) }
    // def *(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded31) = { double_times(self,__arg1.toDouble) }
    // def *(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded32) = { double_times(self,__arg1.toDouble) }
    // def *(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded33) = { double_times(self,__arg1) }
    // def *(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded34) = { double_times(self,__arg1.toDouble) }
    // def *(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded40) = { double_times(self,readVar(__arg1).toDouble) }
    // def *(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded41) = { double_times(self,readVar(__arg1).toDouble) }
    // def *(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded42) = { double_times(self,readVar(__arg1)) }
    // def *(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded43) = { double_times(self,readVar(__arg1).toDouble) }
    // def /(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded21) = { double_divide(self,unit(__arg1.toDouble)) }
    // def /(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded22) = { double_divide(self,unit(__arg1.toDouble)) }
    // def /(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded23) = { double_divide(self,unit(__arg1)) }
    // def /(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded24) = { double_divide(self,unit(__arg1.toDouble)) }
    // def /(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded30) = { double_divide(self,__arg1.toDouble) }
    // def /(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded31) = { double_divide(self,__arg1.toDouble) }
    // def /(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded32) = { double_divide(self,__arg1) }
    // def /(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded33) = { double_divide(self,__arg1.toDouble) }
    // def /(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded40) = { double_divide(self,readVar(__arg1).toDouble) }
    // def /(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded41) = { double_divide(self,readVar(__arg1).toDouble) }
    // def /(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded42) = { double_divide(self,readVar(__arg1)) }
    // def /(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded43) = { double_divide(self,readVar(__arg1).toDouble) }
    def toInt(implicit pos: SourceContext) = double_to_int(self)
    def toFloat(implicit pos: SourceContext) = double_to_float(self)
    def toLong(implicit pos: SourceContext) = double_to_long(self)
  }

  def obj_double_parseDouble(s:Rep[String])(implicit pos: SourceContext): Rep[Double] = ???
  def obj_double_positive_infinity(implicit pos: SourceContext): Rep[Double] = ???
  def obj_double_negative_infinity(implicit pos: SourceContext): Rep[Double] = ???
  def obj_double_min_value(implicit pos: SourceContext): Rep[Double] = ???
  def obj_double_max_value(implicit pos: SourceContext): Rep[Double] = ???
  def double_plus(lhs: Rep[Double], rhs: Rep[Double])(implicit pos: SourceContext): Rep[Double] = ???
  def double_minus(lhs: Rep[Double], rhs: Rep[Double])(implicit pos: SourceContext): Rep[Double] = ???
  def double_times(lhs: Rep[Double], rhs: Rep[Double])(implicit pos: SourceContext): Rep[Double] = ???
  def double_divide(lhs: Rep[Double], rhs: Rep[Double])(implicit pos: SourceContext): Rep[Double] = ???
  def double_to_int(lhs: Rep[Double])(implicit pos: SourceContext): Rep[Int] = ???
  def double_to_float(lhs: Rep[Double])(implicit pos: SourceContext): Rep[Float] = ???
  def double_to_long(lhs: Rep[Double])(implicit pos: SourceContext): Rep[Long] = ???


  /**
   * Float
   */
  object Float {
    def parseFloat(s: Rep[String])(implicit pos: SourceContext) = obj_float_parse_float(s)
  }

  implicit def repToFloatOpsCls(x: Rep[Float])(implicit pos: SourceContext) = new FloatOpsCls(x)(pos)
  implicit def liftToFloatOpsCls(x: Float)(implicit pos: SourceContext) = new FloatOpsCls(unit(x))(pos)
  implicit def varToFloatOpsCls(x: Var[Float])(implicit pos: SourceContext) = new FloatOpsCls(readVar(x))(pos)

  class FloatOpsCls(val self: Rep[Float])(implicit pos: SourceContext) {
    // def +(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded18) = { float_plus(self,unit(__arg1.toFloat)) }
    // def +(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded19) = { float_plus(self,unit(__arg1)) }
    // def +(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded20) = { double_plus(self.toDouble,unit(__arg1)) }
    // def +(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded21) = { float_plus(self,unit(__arg1.toFloat)) }
    // def +(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded27) = { float_plus(self,__arg1.toFloat) }
    // def +(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded28) = { float_plus(self,__arg1) }
    // def +(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded29) = { double_plus(self.toDouble,__arg1) }
    // def +(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded30) = { float_plus(self,__arg1.toFloat) }
    // def +(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded37) = { float_plus(self,readVar(__arg1).toFloat) }
    // def +(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded38) = { float_plus(self,readVar(__arg1)) }
    // def +(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded39) = { double_plus(self.toDouble,readVar(__arg1)) }
    // def +(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded40) = { float_plus(self,readVar(__arg1).toFloat) }
    // def -(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded18) = { float_minus(self,unit(__arg1.toFloat)) }
    // def -(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded19) = { float_minus(self,unit(__arg1)) }
    // def -(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded20) = { double_minus(self.toDouble,unit(__arg1)) }
    // def -(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded21) = { float_minus(self,unit(__arg1.toFloat)) }
    // def -(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded27) = { float_minus(self,__arg1.toFloat) }
    // def -(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded28) = { float_minus(self,__arg1) }
    // def -(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded29) = { double_minus(self.toDouble,__arg1) }
    // def -(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded30) = { float_minus(self,__arg1.toFloat) }
    // def -(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded37) = { float_minus(self,readVar(__arg1).toFloat) }
    // def -(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded38) = { float_minus(self,readVar(__arg1)) }
    // def -(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded39) = { double_minus(self.toDouble,readVar(__arg1)) }
    // def -(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded40) = { float_minus(self,readVar(__arg1).toFloat) }
    // def *(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded19) = { float_times(self,unit(__arg1.toFloat)) }
    // def *(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded20) = { float_times(self,unit(__arg1)) }
    // def *(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded21) = { double_times(self.toDouble,unit(__arg1)) }
    // def *(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded22) = { float_times(self,unit(__arg1.toFloat)) }
    // def *(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded28) = { float_times(self,__arg1.toFloat) }
    // def *(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded29) = { float_times(self,__arg1) }
    // def *(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded30) = { double_times(self.toDouble,__arg1) }
    // def *(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded31) = { float_times(self,__arg1.toFloat) }
    // def *(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded37) = { float_times(self,readVar(__arg1).toFloat) }
    // def *(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded38) = { float_times(self,readVar(__arg1)) }
    // def *(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded39) = { double_times(self.toDouble,readVar(__arg1)) }
    // def *(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded40) = { float_times(self,readVar(__arg1).toFloat) }
    // def /(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded18) = { float_divide(self,unit(__arg1.toFloat)) }
    // def /(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded19) = { float_divide(self,unit(__arg1)) }
    // def /(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded20) = { double_divide(self.toDouble,unit(__arg1)) }
    // def /(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded21) = { float_divide(self,unit(__arg1.toFloat)) }
    // def /(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded27) = { float_divide(self,__arg1.toFloat) }
    // def /(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded28) = { float_divide(self,__arg1) }
    // def /(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded29) = { double_divide(self.toDouble,__arg1) }
    // def /(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded30) = { float_divide(self,__arg1.toFloat) }
    // def /(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded37) = { float_divide(self,readVar(__arg1).toFloat) }
    // def /(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded38) = { float_divide(self,readVar(__arg1)) }
    // def /(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded39) = { double_divide(self.toDouble,readVar(__arg1)) }
    // def /(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded40) = { float_divide(self,readVar(__arg1).toFloat) }
    def toInt(implicit pos: SourceContext) = float_to_int(self)
    def toDouble(implicit pos: SourceContext) = float_to_double(self)
  }

  def obj_float_parse_float(s: Rep[String])(implicit pos: SourceContext): Rep[Float] = ???
  def float_plus(lhs: Rep[Float], rhs: Rep[Float])(implicit pos: SourceContext): Rep[Float] = ???
  def float_minus(lhs: Rep[Float], rhs: Rep[Float])(implicit pos: SourceContext): Rep[Float] = ???
  def float_times(lhs: Rep[Float], rhs: Rep[Float])(implicit pos: SourceContext): Rep[Float] = ???
  def float_divide(lhs: Rep[Float], rhs: Rep[Float])(implicit pos: SourceContext): Rep[Float] = ???
  def float_to_int(lhs: Rep[Float])(implicit pos: SourceContext): Rep[Int] = ???
  def float_to_double(lhs: Rep[Float])(implicit pos: SourceContext): Rep[Double] = ???


  /**
   * Int
   */

  object Int {
    def MaxValue(implicit pos: SourceContext) = obj_int_max_value
    def MinValue(implicit pos: SourceContext) = obj_int_min_value
  }

  object Integer {
    def parseInt(s: Rep[String])  = obj_integer_parseInt(s)
  }

  implicit def intToIntOps(n: Int): IntOpsCls = new IntOpsCls(unit(n))
  implicit def repIntToIntOps(n: Rep[Int]): IntOpsCls = new IntOpsCls(n)
  implicit def varIntToIntOps(n: Var[Int]): IntOpsCls = new IntOpsCls(readVar(n))

  class IntOpsCls(self: Rep[Int])(implicit pos: SourceContext) {
    // def +(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded15) = { int_plus(self,unit(__arg1)) }
    // def +(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded16) = { float_plus(self.toFloat,unit(__arg1)) }
    // def +(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded17) = { double_plus(self.toDouble,unit(__arg1)) }
    // def +(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded18) = { long_plus(self.toLong,unit(__arg1)) }
    // def +(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded24) = { int_plus(self,__arg1) }
    // def +(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded25) = { float_plus(self.toFloat,__arg1) }
    // def +(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded26) = { double_plus(self.toDouble,__arg1) }
    // def +(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded27) = { long_plus(self.toLong,__arg1) }
    // def +(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded33) = { int_plus(self,readVar(__arg1)) }
    // def +(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded34) = { float_plus(self.toFloat,readVar(__arg1)) }
    // def +(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded35) = { double_plus(self.toDouble,readVar(__arg1)) }
    // def +(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded36) = { long_plus(self.toLong,readVar(__arg1)) }
    // def -(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded15) = { int_minus(self,unit(__arg1)) }
    // def -(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded16) = { float_minus(self.toFloat,unit(__arg1)) }
    // def -(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded17) = { double_minus(self.toDouble,unit(__arg1)) }
    // def -(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded18) = { long_minus(self.toLong,unit(__arg1)) }
    // def -(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded24) = { int_minus(self,__arg1) }
    // def -(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded25) = { float_minus(self.toFloat,__arg1) }
    // def -(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded26) = { double_minus(self.toDouble,__arg1) }
    // def -(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded27) = { long_minus(self.toLong,__arg1) }
    // def -(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded33) = { int_minus(self,readVar(__arg1)) }
    // def -(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded34) = { float_minus(self.toFloat,readVar(__arg1)) }
    // def -(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded35) = { double_minus(self.toDouble,readVar(__arg1)) }
    // def -(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded36) = { long_minus(self.toLong,readVar(__arg1)) }
    // def *(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded16) = { int_times(self,unit(__arg1)) }
    // def *(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded17) = { float_times(self.toFloat,unit(__arg1)) }
    // def *(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded18) = { double_times(self.toDouble,unit(__arg1)) }
    // def *(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded19) = { long_times(self.toLong,unit(__arg1)) }
    // def *(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded25) = { int_times(self,__arg1) }
    // def *(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded26) = { float_times(self.toFloat,__arg1) }
    // def *(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded27) = { double_times(self.toDouble,__arg1) }
    // def *(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded28) = { long_times(self.toLong,__arg1) }
    // def *(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded33) = { int_times(self,readVar(__arg1)) }
    // def *(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded34) = { float_times(self.toFloat,readVar(__arg1)) }
    // def *(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded35) = { double_times(self.toDouble,readVar(__arg1)) }
    // def *(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded36) = { long_times(self.toLong,readVar(__arg1)) }
    // def /(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded15) = { int_divide(self,unit(__arg1)) }
    // def /(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded16) = { float_divide(self.toFloat,unit(__arg1)) }
    // def /(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded17) = { double_divide(self.toDouble,unit(__arg1)) }
    // def /(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded18) = { long_divide(self.toLong,unit(__arg1)) }
    // def /(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded24) = { int_divide(self,__arg1) }
    // def /(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded25) = { float_divide(self.toFloat,__arg1) }
    // def /(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded26) = { double_divide(self.toDouble,__arg1) }
    // def /(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded27) = { long_divide(self.toLong,__arg1) }
    // def /(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded33) = { int_divide(self,readVar(__arg1)) }
    // def /(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded34) = { float_divide(self.toFloat,readVar(__arg1)) }
    // def /(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded35) = { double_divide(self.toDouble,readVar(__arg1)) }
    // def /(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded36) = { long_divide(self.toLong,readVar(__arg1)) }
    def %(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded1) = int_mod(self, __arg1)
    def &(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded1) = int_binaryand(self, __arg1)
    def |(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded1) = int_binaryor(self, __arg1)
    def ^(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded1) = int_binaryxor(self, __arg1)
    def <<(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded1) = int_leftshift(self, __arg1)
    def >>(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded1) = int_rightshiftarith(self, __arg1)
    def >>>(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded1) = int_rightshiftlogical(self, __arg1)
    def unary_~(implicit pos: SourceContext) = int_bitwise_not(self)
    def toLong(implicit pos: SourceContext) = int_to_long(self)
    def toDouble(implicit pos: SourceContext) = int_to_double(self)
    def toFloat(implicit pos: SourceContext) = int_to_float(self)
  }

  def obj_integer_parseInt(s: Rep[String])(implicit pos: SourceContext): Rep[Int] = ???
  def obj_int_max_value(implicit pos: SourceContext): Rep[Int] = ???
  def obj_int_min_value(implicit pos: SourceContext): Rep[Int] = ???
  def int_plus(lhs: Rep[Int], rhs: Rep[Int])(implicit pos: SourceContext): Rep[Int] =
    Wrap((Adapter.INT(Unwrap(lhs)) + Adapter.INT(Unwrap(rhs))).x)
  def int_minus(lhs: Rep[Int], rhs: Rep[Int])(implicit pos: SourceContext): Rep[Int] =
    Wrap((Adapter.INT(Unwrap(lhs)) - Adapter.INT(Unwrap(rhs))).x)
  def int_times(lhs: Rep[Int], rhs: Rep[Int])(implicit pos: SourceContext): Rep[Int] =
    Wrap((Adapter.INT(Unwrap(lhs)) * Adapter.INT(Unwrap(rhs))).x)
  def int_divide(lhs: Rep[Int], rhs: Rep[Int])(implicit pos: SourceContext): Rep[Int] =
    Wrap((Adapter.INT(Unwrap(lhs)) / Adapter.INT(Unwrap(rhs))).x)

  def int_mod(lhs: Rep[Int], rhs: Rep[Int])(implicit pos: SourceContext): Rep[Int] = ???
  def int_binaryor(lhs: Rep[Int], rhs: Rep[Int])(implicit pos: SourceContext): Rep[Int] = ???
  def int_binaryand(lhs: Rep[Int], rhs: Rep[Int])(implicit pos: SourceContext): Rep[Int] = 
    Wrap(Adapter.g.reflect("&", Unwrap(lhs), Unwrap(rhs)))
  def int_binaryxor(lhs: Rep[Int], rhs: Rep[Int])(implicit pos: SourceContext): Rep[Int] = ???
  def int_bitwise_not(lhs: Rep[Int])(implicit pos: SourceContext) : Rep[Int] = ???
  def int_to_long(lhs: Rep[Int])(implicit pos: SourceContext) : Rep[Long] = ???
  def int_to_float(lhs: Rep[Int])(implicit pos: SourceContext) : Rep[Float] = ???
  def int_to_double(lhs: Rep[Int])(implicit pos: SourceContext) : Rep[Double] = ???
  def int_leftshift(lhs: Rep[Int], rhs: Rep[Int])(implicit pos: SourceContext): Rep[Int] = ???
  def int_rightshiftarith(lhs: Rep[Int], rhs: Rep[Int])(implicit pos: SourceContext): Rep[Int] = ???
  def int_rightshiftlogical(lhs: Rep[Int], rhs: Rep[Int])(implicit pos: SourceContext): Rep[Int] = ???

  /**
   * Char
   */

  implicit def charToCharOps(self: Rep[Char]) = new CharOpsCls(self)

  class CharOpsCls(self: Rep[Char])(implicit pos: SourceContext) {
    def toInt(implicit pos: SourceContext) = char_toInt(self)
  }

  def char_toInt(lhs: Rep[Char])(implicit pos: SourceContext): Rep[Int] = 
    Wrap(Adapter.g.reflect("Char.toInt", Unwrap(lhs)))

  /**
   * Long
   */

  object Long {
    def parseLong(s: Rep[String])(implicit pos: SourceContext) = obj_long_parse_long(s)
    def MaxValue(implicit pos: SourceContext) = obj_long_max_value
    def MinValue(implicit pos: SourceContext) = obj_long_min_value
  }

  implicit def longToLongOps(n: Long): LongOpsCls = new LongOpsCls(unit(n))
  implicit def repLongToLongOps(n: Rep[Long]): LongOpsCls = new LongOpsCls(n)
  implicit def varLongToLongOps(n: Var[Long]): LongOpsCls = new LongOpsCls(readVar(n))

  class LongOpsCls(self: Rep[Long])(implicit pos: SourceContext) {
    // def +(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded15) = { long_plus(self,unit(__arg1.toLong)) }
    // def +(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded16) = { float_plus(self.toFloat,unit(__arg1)) }
    // def +(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded17) = { double_plus(self.toDouble,unit(__arg1)) }
    // def +(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded18) = { long_plus(self,unit(__arg1)) }
    // def +(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded24) = { long_plus(self,__arg1.toLong) }
    // def +(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded25) = { float_plus(self.toFloat,__arg1) }
    // def +(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded26) = { double_plus(self.toDouble,__arg1) }
    // def +(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded27) = { long_plus(self,__arg1) }
    // def +(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded30) = { long_plus(self,readVar(__arg1).toLong) }
    // def +(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded31) = { float_plus(self.toFloat,readVar(__arg1)) }
    // def +(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded32) = { double_plus(self.toDouble,readVar(__arg1)) }
    // def +(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded33) = { long_plus(self,readVar(__arg1)) }
    // def -(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded15) = { long_minus(self,unit(__arg1.toLong)) }
    // def -(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded16) = { float_minus(self.toFloat,unit(__arg1)) }
    // def -(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded17) = { double_minus(self.toDouble,unit(__arg1)) }
    // def -(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded18) = { long_minus(self,unit(__arg1.toLong)) }
    // def -(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded24) = { long_minus(self,__arg1) }
    // def -(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded25) = { float_minus(self.toFloat,__arg1) }
    // def -(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded26) = { double_minus(self.toDouble,__arg1) }
    // def -(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded27) = { long_minus(self,__arg1) }
    // def -(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded30) = { long_minus(self,readVar(__arg1).toLong) }
    // def -(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded31) = { float_minus(self.toFloat,readVar(__arg1)) }
    // def -(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded32) = { double_minus(self.toDouble,readVar(__arg1)) }
    // def -(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded33) = { long_minus(self,readVar(__arg1)) }
    // def *(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded16) = { long_times(self,unit(__arg1.toLong)) }
    // def *(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded17) = { float_times(self.toFloat,unit(__arg1)) }
    // def *(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded18) = { double_times(self.toDouble,unit(__arg1)) }
    // def *(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded19) = { long_times(self,unit(__arg1.toLong)) }
    // def *(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded25) = { long_times(self,__arg1.toLong) }
    // def *(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded26) = { float_times(self.toFloat,__arg1) }
    // def *(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded27) = { double_times(self.toDouble,__arg1) }
    // def *(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded28) = { long_times(self,__arg1) }
    // def *(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded30) = { long_times(self,readVar(__arg1).toLong) }
    // def *(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded31) = { float_times(self.toFloat,readVar(__arg1)) }
    // def *(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded32) = { double_times(self.toDouble,readVar(__arg1)) }
    // def *(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded33) = { long_times(self,readVar(__arg1)) }
    // def /(__arg1: Int)(implicit pos: SourceContext,__imp1: Overloaded15) = { long_divide(self,unit(__arg1.toLong)) }
    // def /(__arg1: Float)(implicit pos: SourceContext,__imp1: Overloaded16) = { float_divide(self.toFloat,unit(__arg1)) }
    // def /(__arg1: Double)(implicit pos: SourceContext,__imp1: Overloaded17) = { double_divide(self.toDouble,unit(__arg1)) }
    // def /(__arg1: Long)(implicit pos: SourceContext,__imp1: Overloaded18) = { long_divide(self,unit(__arg1)) }
    // def /(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded24) = { long_divide(self,__arg1.toLong) }
    // def /(__arg1: Rep[Float])(implicit pos: SourceContext,__imp1: Overloaded25) = { float_divide(self.toFloat,__arg1) }
    // def /(__arg1: Rep[Double])(implicit pos: SourceContext,__imp1: Overloaded26) = { double_divide(self.toDouble,__arg1) }
    // def /(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded27) = { long_divide(self,__arg1) }
    // def /(__arg1: Var[Int])(implicit pos: SourceContext,__imp1: Overloaded30) = { long_divide(self,readVar(__arg1).toLong) }
    // def /(__arg1: Var[Float])(implicit pos: SourceContext,__imp1: Overloaded31) = { float_divide(self.toFloat,readVar(__arg1)) }
    // def /(__arg1: Var[Double])(implicit pos: SourceContext,__imp1: Overloaded32) = { double_divide(self.toDouble,readVar(__arg1)) }
    // def /(__arg1: Var[Long])(implicit pos: SourceContext,__imp1: Overloaded33) = { long_divide(self,readVar(__arg1)) }
    def %(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded1) = long_mod(self, __arg1)
    def &(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded1) = long_binaryand(self, __arg1)
    def |(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded1) = long_binaryor(self, __arg1)
    def ^(__arg1: Rep[Long])(implicit pos: SourceContext,__imp1: Overloaded1) = long_binaryxor(self, __arg1)
    def <<(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded1) = long_shiftleft(self, __arg1)
    def >>(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded1) = long_shiftright_signed(self, __arg1)
    def >>>(__arg1: Rep[Int])(implicit pos: SourceContext,__imp1: Overloaded1) = long_shiftright_unsigned(self, __arg1)
    def toInt(implicit pos: SourceContext) = long_to_int(self)
    def toDouble(implicit pos: SourceContext) = long_to_double(self)
    def toFloat(implicit pos: SourceContext) = long_to_float(self)
  }

  def long_plus(lhs: Rep[Long], rhs: Rep[Long])(implicit pos: SourceContext): Rep[Long] = 
    Wrap((Adapter.INT(Unwrap(lhs)) + Adapter.INT(Unwrap(rhs))).x) // XXX type
  def long_minus(lhs: Rep[Long], rhs: Rep[Long])(implicit pos: SourceContext): Rep[Long] = 
    Wrap((Adapter.INT(Unwrap(lhs)) - Adapter.INT(Unwrap(rhs))).x) // XXX type    
  def long_times(lhs: Rep[Long], rhs: Rep[Long])(implicit pos: SourceContext): Rep[Long] = 
    Wrap((Adapter.INT(Unwrap(lhs)) * Adapter.INT(Unwrap(rhs))).x) // XXX type
  def long_divide(lhs: Rep[Long], rhs: Rep[Long])(implicit pos: SourceContext): Rep[Long] = 
    Wrap((Adapter.INT(Unwrap(lhs)) / Adapter.INT(Unwrap(rhs))).x) // XXX type

  def obj_long_parse_long(s: Rep[String])(implicit pos: SourceContext): Rep[Long] = ???
  def obj_long_max_value(implicit pos: SourceContext): Rep[Long] = ???
  def obj_long_min_value(implicit pos: SourceContext): Rep[Long] = ???
  def long_mod(lhs: Rep[Long], rhs: Rep[Long])(implicit pos: SourceContext): Rep[Long] = ???
  def long_binaryand(lhs: Rep[Long], rhs: Rep[Long])(implicit pos: SourceContext): Rep[Long] = ???
  def long_binaryor(lhs: Rep[Long], rhs: Rep[Long])(implicit pos: SourceContext): Rep[Long] = ???
  def long_binaryxor(lhs: Rep[Long], rhs: Rep[Long])(implicit pos: SourceContext): Rep[Long] = ???
  def long_shiftleft(lhs: Rep[Long], rhs: Rep[Int])(implicit pos: SourceContext): Rep[Long] = ???
  def long_shiftright_signed(lhs: Rep[Long], rhs: Rep[Int])(implicit pos: SourceContext): Rep[Long] = ???
  def long_shiftright_unsigned(lhs: Rep[Long], rhs: Rep[Int])(implicit pos: SourceContext): Rep[Long] = ???
  def long_to_int(lhs: Rep[Long])(implicit pos: SourceContext): Rep[Int] = 
    Wrap(Adapter.g.reflect("Long.toInt", Unwrap(lhs)))
  def long_to_float(lhs: Rep[Long])(implicit pos: SourceContext): Rep[Float] = ???
  def long_to_double(lhs: Rep[Long])(implicit pos: SourceContext): Rep[Double] = ???
}

