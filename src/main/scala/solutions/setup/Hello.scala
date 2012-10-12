package solutions.setup

import java.io.PrintWriter

import scala.virtualization.lms.common._

object Hello {

  object PrintIterable extends IterableOpsExp with MiscOpsExp {
    def apply(x: Rep[Unit]): Rep[Unit] =
      for (i <- unit(0 until 10)) { println(i) }
  }

  object PrintUnroll extends MiscOpsExp {
    def apply(x: Rep[Unit]): Rep[Unit] =
      for (i <- 0 until 10) { println(unit(i)) }
  }

  object PrintRange extends RangeOpsExp with MiscOpsExp {
    def apply(x: Rep[Unit]): Rep[Unit] =
      for (i <- unit(0) until unit(10)) { println(i) }
  }

  object PrintImplicitRange extends RangeOpsExp with LiftNumeric with MiscOpsExp {
    def apply(x: Rep[Unit]): Rep[Unit] =
      for (i <- 0 until 10) { println(i) }
  }

  def main(args: Array[String]): Unit = {

    val writer = new java.io.PrintWriter(System.out)

//    trait ScalaGenPrintln extends ScalaGenMiscOps with ScalaGenStringOps// with ScalaGenObjectOps

    new ScalaGenIterableOps with ScalaGenMiscOps {
      val IR: PrintIterable.type = PrintIterable
    }.emitSource(PrintIterable.apply, "PrintIterable", writer)

    new ScalaGenMiscOps {
      val IR: PrintUnroll.type = PrintUnroll
    }.emitSource(PrintUnroll.apply, "PrintUnroll", writer)

    new ScalaGenRangeOps with ScalaGenMiscOps {
      val IR: PrintRange.type = PrintRange
    }.emitSource(PrintRange.apply, "PrintRange", writer)

    new ScalaGenRangeOps with ScalaGenMiscOps {
      val IR: PrintImplicitRange.type = PrintImplicitRange
    }.emitSource(PrintImplicitRange.apply, "PrintImplicitRange", writer)
  }
}
