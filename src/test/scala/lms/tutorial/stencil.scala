/**
Sliding Stencil
===============

In this tutorial, we show a solution to Shonan Challenge 3.3 Stencil.

For explanation of this solution, [see the group discussion](https://groups.google.com/d/msg/stagedhpc/r5L4xGJETwE/1kOdwJo0kgAJ).

For further reference, see:

- Shonan Challenge for Generative Programming ([PDF](http://homes.sice.indiana.edu/ccshan/metafx/pepm2013.pdf)) ([official code repository](https://github.com/StagedHPC/shonan-challenge))
  Baris Aktemur, Yukiyoshi Kameyama, Oleg Kiselyov, Chung-chieh Shan. PEPM'13

Outline:
<div id="tableofcontents"></div>

*/

package scala.lms.tutorial

import scala.lms.common.ForwardTransformer
import scala.reflect.SourceContext

/**
## Stencil Challenge

We implement the challenge using an almost regular staged loop,
except using `sliding` staging-time abstraction.

How does it work? We rely on the fact that LMS performs common
subexpression elimination internally, and we insert variables for
values that are re-used from one loop iteration to the next.

We compute the code for loop iterations i and i + 1. For iteration i + 1,
we substitute i + 1 for i.

We peel the first loop iteration to initialize the variables.

And then generate a modified loop that executes the remaining
iterations. The loop body corresponds to the (i+1) iteration above
plus the reads and write statements that maintain the variables.

So with a twist on our general CSE facility, we implement CSE across
loop iterations. The approach also works for window sizes larger than
1, we just need to unroll more iterations of the loop.
In the challenge example, if we look at iteration i + 2, we find that
it does not reuse any memory access operations from iteration i, so
a larger window is not necessary in this case.

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

Note that for the examples here, the multi sliding does not improve
upon the number of shared variables.
*/


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

Not actually sliding -- just to have a baseline reference.
*/
trait NoSlidingExp extends DslExp with Sliding {
  def sliding(start: Rep[Int], end: Rep[Int])(f: Rep[Int] => Rep[Unit]): Rep[Unit] = {
    (start until end) foreach f
  }
}

/**
### Sliding
*/
trait SlidingExp extends DslExp with Sliding {
/**
We use a LMS transformer to evaluate the loop body
across various iterations and transformations.
*/
  object trans extends ForwardTransformer {
    val IR: SlidingExp.this.type = SlidingExp.this
  }
  def log(x: Any): Unit = {
    System.out.println("sliding log: "+x)
  }

/**
Some arithmetic rewrites to maximize sliding sharing.
*/

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

  type Subst = scala.collection.immutable.Map[Exp[Any],Exp[Any]]
  type Doublet = (Rep[Unit], List[Stm])
  type Triplet = (Rep[Unit], List[Stm], Subst)

/**
Find the overlapping symbols: defined by f(i), used by f(i+1), f(i+2), ...
We create a helper function so that we can generalize to a fixpoint calculation
in the subclass below.
*/

  def findOverlap(i: Sym[Int], f: Rep[Int] => Rep[Unit]): (List[Sym[Any]], Doublet, Triplet) = {

/**
We evaluate loop contents f(i), then f(i+1), then f(i+2).
For evaluation, we use the reified sub graph for f(i), but we substitute i for i+1 or i+2.
Each time, we reflect the statements from the previous iteration.
*/

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
/**
 Here, we find the overlap symbols: defined by f(i), used by f(i+1) and f(i+2).
*/
    val overlap01 = stms1.flatMap { case TP(s,d) => syms(d) filter (defs contains _) }.distinct
    val overlap02 = stms2.flatMap { case TP(s,d) => syms(d) filter (defs contains _) }.distinct

/**
 We haven't yet a fixed point yet, if there are still overlapping symbols in the last iteration.
 We will fix this in the subclass below.
*/
    if (overlap02.nonEmpty)
      log("Overlap beyond a single loop iteration is ignored, since not yet implemented.")

    val overlap0 = (overlap01++overlap02).distinct
    (overlap0, (r0, stms0), (r1, stms1, subst1))
  }

  def sliding(start: Rep[Int], end: Rep[Int])(f: Rep[Int] => Rep[Unit]): Rep[Unit] = {
    val i = fresh[Int]
    val (overlap0, (r0, stms0), (r1, stms1, subst1)) = findOverlap(i, f)
    val overlap1 = overlap0 map subst1
/**
Build a variable for each overlap symbol.
Initialize the variables by peeling first loop iteration.
*/
    if (end > start) {
      val (rX,substX) = trans.withSubstScope(i -> start) {
        stms0.foreach(s=>trans.traverseStm(s))
        (trans(r0), trans.subst)
      }
      val vars = overlap0.map{x => var_new(substX(x))(x.tp,x.pos.head)}
/**
Now generate the loop:
*/
      for (j <- (start + unit(1)) until end) {
/**
- Read the overlap variables.
*/
        generate_comment("variable reads")
        val reads = (overlap0 zip vars).map(p => (p._1, readVar(p._2)(p._1.tp,p._1.pos.head)))
/**
- Emit the transformed loop body.
*/
        generate_comment("computation")
        val (ri, substY1: Subst) = trans.withSubstScope((reads:+(i->(j-unit(1)))): _*) {
          stms1.foreach(s=>trans.traverseStm(s))
          (trans(r1), trans.subst)
        }
/**
- Write the new values to the overlapping variables.
*/
        generate_comment("variable writes")
        val writes = (overlap1 zip vars).map{p =>
          (p._1, var_assign(p._2, substY1(p._1))(p._1.tp,p._1.pos.head))}
      }
    }
  }
}

/**
### Multi sliding
*/
trait SlidingMultiExp extends SlidingExp with DslExp with Sliding {
/**
Find overlapping symbols between iterations until there are no new ones.
*/
  override def findOverlap(i: Sym[Int], f: Rep[Int] => Rep[Unit]) = {
    val save = context
    val (r0,stms0) = reifySubGraph(f(i))
    val defs = stms0.flatMap(_.lhs)
    def step(n: Int, last: List[Stm], acc: List[Triplet], overlap: List[Sym[Any]]): (Triplet, List[Sym[Any]]) = {
      val (res: (Triplet, List[Sym[Any]]), _) = reifySubGraph {
        reflectSubGraph(last)
        context = save
        val ((ri, substi), stmsi) = reifySubGraph(trans.withSubstScope(i -> (i+n)) {
          stms0.foreach(s => trans.traverseStm(s))
          (trans(r0), trans.subst)
        })

        val overlapi = stmsi.flatMap{ case TP(s,d) => syms(d) filter (defs contains _)}.distinct
        if (overlapi.nonEmpty)
          step(n+1, stmsi, ((ri, stmsi, substi):Triplet)::acc, (overlap++overlapi).distinct)
        else {
          log("stopping at "+n)
          (acc.last, overlap)
        }
      }
      res
    }
    val ((r1, stms1, subst1), overlap0) = step(1, stms0, Nil, Nil)
    context = save
    (overlap0, (r0, stms0), (r1, stms1, subst1))
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
