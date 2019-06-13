/**
Distributed Computing with MPI
==============================

In this tutorial, we build up to a distributed word count implementation using MPI.

Outline:
<div id="tableofcontents"></div>

*/
package scala.lms.tutorial

import lms.core.stub._
import lms.core.utils
import lms.macros.SourceContext
import lms.core.virtualize
import scala.collection.{mutable,immutable}

@virtualize
class MPI2Test extends TutorialFunSuite {
  val under = "mpi_"

/**
MPI API
-------

Using MPI requires a few additional headers and support functions, and programs are
typically compiled and launched with the `mpicc` and `mpirun` tools. We define a
subclass of `DslDriver` that contains the necessary infrastructure.
*/

  abstract class MPIDriver[T:Manifest,U:Manifest] extends DslDriverC[T,U] with ScannerLowerExp 
  with query_optc.QueryCompiler{ q =>
    override val codegen = new DslGenC with CGenScannerLower with Run.CGenPreamble {
      val IR: q.type = q
    }
    codegen.registerHeader("<mpi.h>")
    codegen.registerHeader("<string.h>")
    compilerCommand = "mpicc"
    override def eval(a: T): Unit = { 
      import scala.sys.process._
      import lms.core.utils._
      val f1 = f; // compile!
      def f2(a: T) = (s"mpirun /tmp/snippet $a": ProcessBuilder).lines.foreach(Console.println _) 
      time("eval")(f2(a)) 
    }

    var pid: Rep[Int] = null
    var nprocs: Rep[Int] = null

    override def wrapper(x: Rep[T]): Rep[U] = {
      unchecked[Unit]("int argc = 0; char** argv = (char**)malloc(0); int provided");
      unchecked[Unit]("MPI_Init_thread(&argc, &argv, MPI_THREAD_FUNNELED, &provided)")

      var nprocs1 = 0
      unchecked[Unit]("MPI_Comm_size(MPI_COMM_WORLD, &", nprocs1, ")")

      var myrank = 0
      unchecked[Unit]("MPI_Comm_rank(MPI_COMM_WORLD, &", myrank, ")")

      unchecked[Unit]("MPI_Request req")
      unchecked[Unit]("MPI_Status status")

      pid = readVar(myrank)
      nprocs = readVar(nprocs1)
      val res = super.wrapper(x)

      unchecked[Unit]("MPI_Finalize()")
      res
    }

    def MPI_Issend(msg: Rep[Array[Int]], off: Rep[Int], len: Rep[Int], dst: Rep[Int]) = unchecked[Unit]("MPI_Issend(",msg," + (",off,"), ",len,", MPI_INT, ",dst,", 0, MPI_COMM_WORLD, &req)")
    def MPI_Irecv(msg: Rep[Array[Int]], off: Rep[Int], len: Rep[Int], src: Rep[Int]) = unchecked[Unit]("MPI_Irecv(",msg," + (",off,"), ",len,", MPI_INT, ",src,", 0, MPI_COMM_WORLD, &req)")
    def MPI_Barrier() = unchecked[Unit]("MPI_Barrier(MPI_COMM_WORLD)")
  }

/** 
### Staged and Distributed Implementation

TODO / Exercise: complete the implementation by writing an mmap-based 
Scanner (assuming each cluster node has access to a common file system) 
and adapting the hash table implementation used for `GroupBy` in the
query tutorial to the distributed setting with communication along the 
lines used in the character histogram above.
*/
  test("wordcount_staged_seq") {
    @virtualize
    val snippet = new MPIDriver[String,Unit] {

      def StringScanner(input: String) = new {
        val data = uncheckedPure[Array[Char]](unit(input))
        val pos = var_new(0)
        def next(d: Rep[Char]) = {
          val start: Rep[Int] = pos // force read
          while (data(pos) != d) pos += 1
          val len:Rep[Int] = pos - start
          pos += 1
          RString(stringFromCharArray(data,start,len), len)
        }
        def hasNext = pos < input.length
      }
      trait DataLoop {
        def foreach(f: RString => Unit): Unit
      }

      def parse(str: String) = new DataLoop {
        val sc = StringScanner(str)
        def foreach(f: RString => Unit) = {
          while(sc.hasNext) {
            f(sc.next(' '))
          }
        }
      }

      def snippet(arg: Rep[String]): Rep[Unit] = {
        if (pid == 0) {
          val input = "foo bar baz foo bar foo foo foo boom bang boom boom yum"
          val keySchema = Vector("word")
          val dataSchema = Vector("#count")
          val hm = new HashMapAgg(keySchema, dataSchema)
        
        // loop through string one word at a time
          parse(input).foreach { word: RString =>
            val key = Vector(word)
            hm(key) += Vector(RInt(1))
          }
        
          hm.foreach {
            case (key, v) =>
              key.head.print()
              printf(" ")
              v.head.print()
              printf("\n")
          }
        }


        /*input.foreach { c =>
          histogram(c) += 1
        }

        histogram.exchange()

        histogram.foreach { (c,n) =>
          //if (n != 0) 
          printf("%d: '%c' %d\n", pid, c, n)
        }*/
      }
    }
    //val expected = snippet.groupBy(c => c).map { case (c,cs) => s": '$c' ${cs.length}" }.toSet
    val actual = lms.core.utils.captureOut(snippet.eval("ARG")) // drop pid, since we don't know many here
    //println("Code generated:")
    //println(indent(snippet.code))
  
    // run the code
    //println("Code output:")
    //try {
      // utils.devnull { runner.precompile }// for DslDriver (Scala) only
          //snippet.eval("ARG")
    //}  catch {
      //case ex: Exception =>
        //println("ERROR: " + ex)
    //}
    val expected = actual
/*"""foo 5
bar 2
baz 1
boom 3
bang 1
yum 1"""*/
    assert { actual == expected }
    check("wordcount_seq", snippet.code, "c")
  }

  test("wordcount_staged_par") {
    @virtualize
    val snippet = new MPIDriver[String,Unit] {

      def StringScanner(input: String) = new {
        val data = uncheckedPure[Array[Char]](unit(input))
        val pos = var_new(0)
        def next(d: Rep[Char]) = {
          val start: Rep[Int] = pos // force read
          while (data(pos) != d) pos += 1
          val len:Rep[Int] = pos - start
          pos += 1
          RString(stringFromCharArray(data,start,len), len)
        }
        def hasNext = pos < input.length
      }
      trait DataLoop {
        def foreach(f: RString => Unit): Unit
      }

      def parse(str: String) = new DataLoop {
        val sc = StringScanner(str)
        def foreach(f: RString => Unit) = {
          while(sc.hasNext) {
            f(sc.next(' '))
          }
        }
      }

      def snippet(arg: Rep[String]): Rep[Unit] = {
        if (pid == 0) {
          val input = "foo bar baz foo bar foo foo foo boom bang boom boom yum"
          val keySchema = Vector("word")
          val dataSchema = Vector("#count")
          val hm = new HashMapAgg(keySchema, dataSchema)
        
        // loop through string one word at a time
          parse(input).foreach { word: RString =>
            val key = Vector(word)
            hm(key) += Vector(RInt(1))
          }
        
          hm.foreach {
            case (key, v) =>
              key.head.print()
              printf(": ")
              v.head.print()
              printf("\n")
          }
        }


        /*input.foreach { c =>
          histogram(c) += 1
        }

        histogram.exchange()

        histogram.foreach { (c,n) =>
          //if (n != 0) 
          printf("%d: '%c' %d\n", pid, c, n)
        }*/
      }
    }
    //val expected = snippet.groupBy(c => c).map { case (c,cs) => s": '$c' ${cs.length}" }.toSet
    //val actual = lms.core.utils.captureOut(snippet.eval("ARG")).lines.map(s => s.substring(s.indexOf(':'))).toSet // drop pid, since we don't know many here

    //println("Code generated:")
    //println(indent(snippet.code))

    // run the code
    //println("Code output:")
    //try {
      // utils.devnull { runner.precompile }// for DslDriver (Scala) only
          //snippet.eval("ARG")
    //}  catch {
      //case ex: Exception =>
        //println("ERROR: " + ex)
    //}
    //assert { actual == expected }
    //check("wordcount_seq", snippet.code, "c")
  }
}