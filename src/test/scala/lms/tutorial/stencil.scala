/**
Sliding Stencil
===============

In this tutorial, we show a solution to Shonan Challenge 3.3 Stencil.

For explanation of this solution, [see the group discussion](https://groups.google.com/d/msg/stagedhpc/r5L4xGJETwE/1kOdwJo0kgAJ).

For further reference, see:

- Shonan Challenge for Generative Programming ([PDF](https://www.cs.rutgers.edu/~ccshan/metafx/pepm2013.pdf)) ([official code repository](https://github.com/StagedHPC/shonan-challenge))
  Baris Aktemur, Yukiyoshi Kameyama, Oleg Kiselyov, Chung-chieh Shan. PEPM'13

Outline:
<div id="tableofcontents"></div>

*/

package scala.lms.tutorial

import scala.lms.common.ForwardTransformer
import scala.reflect.SourceContext

/**
## Infrastructure
*/
trait Sliding extends Dsl {
  def infix_sliding[T:Typ](n: Rep[Int], f: Rep[Int] => Rep[T]): Rep[Array[T]] = {
    val a = NewArray[T](n)
    sliding(0,n)(i => a(i) = f(i))
    a
  }
  def infix_sliding(r: Rep[Range]) = new {
    def foreach(f: Rep[Int] => Rep[Unit]): Rep[Unit] =
      sliding(r.start, r.end)(f)
  }
  def sliding(start: Rep[Int], end: Rep[Int])(f: Rep[Int] => Rep[Unit]): Rep[Unit]
}

/**
### No Sliding (Baseline)
*/
trait NoSlidingExp extends DslExp with Sliding {
  // not actually sliding -- just to have a baseline reference
  def sliding(start: Rep[Int], end: Rep[Int])(f: Rep[Int] => Rep[Unit]): Rep[Unit] = {
    (start until end) foreach f
  }
}

/**
### Sliding
*/
trait SlidingExp extends DslExp with Sliding {
  object trans extends ForwardTransformer {
    val IR: SlidingExp.this.type = SlidingExp.this
  }
  def log(x: Any): Unit = {
    System.out.println("sliding log: "+x)
  }

  // some arithmetic rewrites to maximize sliding sharing
  override def int_plus(lhs: Exp[Int], rhs: Exp[Int])(implicit pos: SourceContext): Exp[Int] =
    ((lhs,rhs) match {
      // (x+y)+z --> x+(y+z)
      case (Def(IntPlus(x:Exp[Int],Const(y:Int))), Const(z:Int)) => int_plus(x, unit(y+z))
      // (x-y)+z --> x-(y-z)
      case (Def(IntMinus(x:Exp[Int],Const(y:Int))), Const(z:Int)) => int_minus(x, unit(y-z))
      case (x: Exp[Int], Const(z:Int)) if z < 0 => int_minus(x, unit(-z))
      case _ => super.int_plus(lhs,rhs)
    }).asInstanceOf[Exp[Int]]

  override def int_minus(lhs: Exp[Int], rhs: Exp[Int])(implicit pos: SourceContext): Exp[Int] =
    ((lhs,rhs) match {
      // (x-y)-z --> x-(y+z)
      case (Def(IntMinus(x:Exp[Int],Const(y:Int))), Const(z:Int)) => int_minus(x, unit(y+z))
      // (x+y)-z --> x+(y-z)
      case (Def(IntPlus(x:Exp[Int],Const(y:Int))), Const(z:Int)) => int_plus(x, unit(y-z))
      case (x: Exp[Int], Const(z:Int)) if z < 0 => int_plus(x, unit(-z))
      case _ => super.int_minus(lhs,rhs)
    }).asInstanceOf[Exp[Int]]

  def sliding(start: Rep[Int], end: Rep[Int])(f: Rep[Int] => Rep[Unit]): Rep[Unit] = {
    val i = fresh[Int]

    val save = context
    // evaluate loop contents f(i)
    val (r0,stms0) = reifySubGraph(f(i))
    val (((r1,stms1,subst1),(r2,stms2,subst2)), _) = reifySubGraph {
      reflectSubGraph(stms0)
      context = save
      // evaluate loop contents f(i+1)
      val ((r1,subst1),stms1) = reifySubGraph(trans.withSubstScope(i -> (i+1)) {
        stms0.foreach(s=>trans.traverseStm(s))
        (trans(r0), trans.subst)
      })
      val ((r2,stms2,subst2), _) = reifySubGraph {
        reflectSubGraph(stms1)
        context = save
        // evaluate loop contents f(i+2)
        val ((r2,subst2),stms2) = reifySubGraph(trans.withSubstScope(i -> (i+2)) {
          stms0.foreach(s=>trans.traverseStm(s))
          (trans(r0), trans.subst)
        })
        (r2,stms2,subst2)
      }
      ((r1,stms1,subst1), (r2,stms2,subst2))
    }
    context = save

    val defs = stms0.flatMap(_.lhs)
    // find overlap syms: defined by f(i), used by f(i+1) and f(i+2)
    val overlap01 = stms1.flatMap { case TP(s,d) => syms(d) filter (defs contains _) }.distinct
    val overlap02 = stms2.flatMap { case TP(s,d) => syms(d) filter (defs contains _) }.distinct

    if (overlap02.nonEmpty)
      log("Overlap beyond a single loop iteration is ignored, since not yet implemented.")

    val overlap0 = (overlap01++overlap02).distinct
    val overlap1 = overlap0 map subst1
    // build a variable for each overlap sym.
    // init variables by peeling first loop iteration.
    if (end > start) {
      val (rX,substX) = trans.withSubstScope(i -> start) {
        stms0.foreach(s=>trans.traverseStm(s))
        (trans(r0), trans.subst)
      }
      val vars = overlap0 map (x => var_new(substX(x))(x.tp,x.pos.head))
      // now generate the loop
      for (j <- (start + unit(1)) until end) {
        // read the overlap variables
        generate_comment("variable reads")
        val reads = (overlap0 zip vars) map (p => (p._1, readVar(p._2)(p._1.tp,p._1.pos.head)))
        // emit the transformed loop body
        generate_comment("computation")
        val (r,substY1) = trans.withSubstScope((reads:+(i->(j-unit(1)))): _*) {
          stms1.foreach(s=>trans.traverseStm(s))
          (trans(r1), trans.subst)
        }
        // write the new values to the overlap vars
        generate_comment("variable writes")
        val writes = (overlap1 zip vars) map (p =>
          (p._1, var_assign(p._2, substY1(p._1))(p._1.tp,p._1.pos.head)))
      }
    }
  }
}

/**
### Multi sliding
*/
trait SlidingMultiExp extends SlidingExp with DslExp with Sliding {
  type Subst = scala.collection.immutable.Map[Exp[Any],Exp[Any]]
  type Triplet = (Rep[Unit], List[Stm], Subst)

  override def sliding(start: Rep[Int], end: Rep[Int])(f: Rep[Int] => Rep[Unit]): Rep[Unit] = {
    val i = fresh[Int]

    val save = context
    // evaluate loop contents f(i)
    val (r0,stms0) = reifySubGraph(f(i))
    val defs = stms0.flatMap(_.lhs)
    def step(n: Int, last: List[Stm], acc: List[Triplet], overlap: List[Sym[Any]]): (Int, List[Triplet], List[Sym[Any]]) = {
      val (res: (Int, List[Triplet], List[Sym[Any]]), _) = reifySubGraph {
        reflectSubGraph(last)
        context = save
        val ((ri, substi), stmsi) = reifySubGraph(trans.withSubstScope(i -> (i+n)) {
          stms0.foreach(s => trans.traverseStm(s))
          (trans(r0), trans.subst)
        })

        val overlapi = stmsi.flatMap { case TP(s,d) => syms(d) filter (defs contains _) }.distinct
        if (overlapi.nonEmpty)
          step(n+1, stmsi, ((ri, stmsi, substi):Triplet)::acc, (overlap++overlapi).distinct)
        else {
          log("stopping at "+n)
          (n-1, acc, overlap)
        }
      }
      res
    }

    val (n, acc, overlap) = step(1, stms0, Nil, Nil)
    context = save

    val ls = acc.reverse
    val overlap0 = overlap
    val overlaps = ls.map({case (_, _, substi) => overlap0 map substi})
    val overlaps_pred = overlap0::overlaps
    // build a variable for each overlap sym.
    // init variables by peeling first loop iteration.
    if (end > start) {
      val (rX,substX) = trans.withSubstScope(i -> start) {
        stms0.foreach(s=>trans.traverseStm(s))
        (trans(r0), trans.subst)
      }
      val vars = overlap0.map{x => var_new(substX(x))(x.tp,x.pos.head)}
      // now generate the loop
      for (j <- (start + unit(1)) until end) {
        // read the overlap variables
        generate_comment("variable reads")
        val reads = (overlap0 zip vars).map(p => (p._1, readVar(p._2)(p._1.tp,p._1.pos.head)))
        // emit the transformed loop body
        generate_comment("computation")
        val (ri, substY1: Subst) = trans.withSubstScope((reads:+(i->(j-unit(1)))): _*) {
          ls(0)._2.foreach(s=>trans.traverseStm(s))
          (trans(ls(0)._1), trans.subst)
        }
        // write the new values to the overlap vars
        generate_comment("variable writes")
        val writes = (overlaps(0) zip vars).map{p =>
          (p._1, var_assign(p._2, substY1(p._1))(p._1.tp,p._1.pos.head))}
      }
    }
  }
}

/**
## Warmup
*/
trait SlidingWarmup extends Sliding {
  def snippet(n: Rep[Int]): Rep[Array[Int]] = {
    def compute(i: Rep[Int]) = 2*i+3
    n sliding { i => compute(i) + compute(i+1) }
  }
}
abstract class SlidingWarmupDriver extends DslDriver[Int,Array[Int]] with SlidingWarmup
class SlidingWarmupTest extends TutorialFunSuite {
  val under = "sliding"

  test("warmup without sliding") {
    val sliding0 = new SlidingWarmupDriver with NoSlidingExp
    check("0", sliding0.code)
  }

  test("warmup with sliding") {
    val sliding1 = new SlidingWarmupDriver with SlidingExp
    check("1", sliding1.code)
  }

  test("warmup with multi sliding") {
    val sliding2 = new SlidingWarmupDriver with SlidingMultiExp
    check("1", sliding2.code) // same as single sliding
  }

  test("warmup equal") {
    val sliding0 = new SlidingWarmupDriver with NoSlidingExp
    val sliding1 = new SlidingWarmupDriver with SlidingExp
    val sliding2 = new SlidingWarmupDriver with SlidingMultiExp
    val input = 5
    assert(sliding0.eval(input).mkString(",") == sliding1.eval(input).mkString(","))
    assert(sliding0.eval(input).mkString(",") == sliding2.eval(input).mkString(","))
  }
}

/**
### Generated code without sliding
      .. includecode:: ../../../../out/sliding0.check.scala

### Generated code with sliding
      .. includecode:: ../../../../out/sliding1.check.scala

### Generated code with multi sliding
      .. includecode:: ../../../../out/sliding2.check.scala

*/

/**
## Stencil Challenge
*/
trait Stencil extends Sliding {
  def snippet(v: Rep[Array[Double]]): Rep[Array[Double]] = {
    val n = v.length
    val input = v
    val output = NewArray[Double](n)
    def a(j: Rep[Int]) = input(j)
    def w1(j: Rep[Int]) = a(j) * a(j+1)
    def wm(j: Rep[Int]) = a(j) - w1(j) + w1(j-1)
    def w2(j: Rep[Int]) = wm(j) * wm(j+1)
    def b(j: Rep[Int]) = wm(j) - w2(j) + w2(j-1)
    for (i <- (2 until n-2).sliding) {
      output(i) = b(i)
    }
    output
  }
}
abstract class StencilDriver extends DslDriver[Array[Double],Array[Double]] with Stencil
class StencilTest extends TutorialFunSuite {
  val under = "stencil"

  test("stencil without sliding") {
    val stencil0 = new StencilDriver with NoSlidingExp
    check("0", stencil0.code)
  }

  test("stencil with sliding") {
    val stencil1 = new StencilDriver with SlidingExp
    check("1", stencil1.code)
  }

  test("stencil with multi sliding") {
    val stencil2 = new StencilDriver with SlidingMultiExp
    check("2", stencil2.code)
  }

  test("stencil equal") {
    val stencil0 = new StencilDriver with NoSlidingExp
    val stencil1 = new StencilDriver with SlidingExp
    val stencil2 = new StencilDriver with SlidingMultiExp
    val input = Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
    assert(stencil0.eval(input).mkString(",") == stencil1.eval(input).mkString(","))
    assert(stencil0.eval(input).mkString(",") == stencil2.eval(input).mkString(","))
  }
}

/**
### Generated code without sliding
      .. includecode:: ../../../../out/stencil0.check.scala

### Generated code with sliding
      .. includecode:: ../../../../out/stencil1.check.scala

### Generated code with multi sliding
      .. includecode:: ../../../../out/stencil2.check.scala
*/
