package solutions.setup

import java.io.PrintWriter

import scala.virtualization.lms.common._

object Hello {

  object PrintIterable extends RangeOpsExp with IterableOpsExp with LiftNumeric with MiscOpsExp with StaticDataExp
  {
    def apply(x: Rep[Unit]): Rep[Unit] = {
      for (i <- (0 until 10): Rep[Range])             { println(i) }
      for (i <- (0 until 10): Range)                  { println(i) }
      for (i <- staticData(List(1, 2, 3, 5, 7, 11)))  { println(i) }
      for (i <- List(1, 2, 3, 5, 7, 11))              { println(i) }
    }
  }

  def main(args: Array[String]): Unit = {
    new ScalaGenRangeOps with ScalaGenIterableOps with ScalaGenMiscOps with ScalaGenStaticData {
      val IR: PrintIterable.type = PrintIterable
    }.emitSource(PrintIterable.apply, "PrintIterable", new java.io.PrintWriter(System.out))
  }
}
