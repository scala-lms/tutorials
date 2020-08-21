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
class MPITest extends TutorialFunSuite {
  val under = "mpi_"

/**
MPI API
-------

Using MPI requires a few additional headers and support functions, and programs are
typically compiled and launched with the `mpicc` and `mpirun` tools. We define a
subclass of `DslDriver` that contains the necessary infrastructure.
*/

  abstract class MPIDriver[T:Manifest,U:Manifest] extends DslDriverC[T,U] with ScannerLowerExp { q =>
    override val codegen = new DslGenC with CGenScannerLower with Run.CGenPreamble {
      val IR: q.type = q
    }
    codegen.registerHeader("<mpi.h>")
    codegen.registerHeader("<string.h>")
    override val compilerCommand = "mpicc"
    override def eval(a: T): Unit = {
      import scala.sys.process._
      import lms.core.utils._
      val f1 = f; // compile!
      def f2(a: T) = (s"mpirun -np 4 --mca btl_base_warn_component_unused 0 /tmp/snippet $a": ProcessBuilder).lines.foreach(Console.println _)
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
First Step: Distributed Character Histogram
-------------------------------------------

As a first example of map-reduce-style processing, we would like to
compute a character histogram in a distributed fashion, counting the
number of occurances of each character in some input data across
a cluster of machines.

### Sequential Reference Implementation
---------------------------------------

The code below is a sequential reference implementation. The check
`if (pid == 0)` ensures that we only rum one instance of the code,
even if multiple processes are launched (this is how MPI works).
*/

  test("charcount_seq") {
    val str =
      "My name is Ozymandias, King of Kings; " +
      "Look on my Works, ye Mighty, and despair!"

    val snippet = new MPIDriver[String,Unit] {
      def snippet(arg: Rep[String]): Rep[Unit] = {
        if (pid == 0) {
          val input: Rep[String] = str

          val histogram = NewArray[Int](256);
          for (i <- 0 until histogram.length)
            histogram(i) = 0

          for (i <- 0 until input.length)
            histogram(input(i).toInt) += 1

          for (i <- (0 until 256): Rep[Range])
            if (histogram(i) != 0) printf("'%c' %d\n", i.toChar, histogram(i))
        }
      }
    }
    val expected = str.groupBy(c => c).map { case (c,cs) => s"'$c' ${cs.length}" }.toSet
    val actual = lms.core.utils.captureOut(snippet.eval("ARG")).lines.toSet
    assert { actual == expected }
    check("charcount_seq", snippet.code, "c")
  }

/**
### Proper Distributed Implementation
-------------------------------------

We now add abstractions for partitioned input handling and for a
distributed histogram implementation that accumulates values
locally and exchanges and merges them with other processes so
that the result is partioned by keys.
*/

  test("charcount_par") {
    val snippet = new MPIDriver[String,Unit] {

      // A distributed input view, processing a chunk on each node
      case class Input(input: Rep[String]) {
        val chunkLen = (input.length - 1) / nprocs + 1
        def foreach(f: Rep[Char] => Unit): Unit = {
          val start = pid * chunkLen
          val end = if (pid == nprocs - 1) input.length else start + chunkLen
          for (i <- start until end)
            f(input(i))
        }
      }

      // A logical slice of an array
      case class View(data: Rep[Array[Int]], off: Rep[Int], len: Rep[Int]) {
        def apply(i: Rep[Int]) = data(off + i)
        def update(i: Rep[Int], x: Rep[Int]) = data(off + i) = x
        def +=(that: View) = for (j <- 0 until len) this(j) += that(j)
        def send(dst: Rep[Int]) = MPI_Issend(data,off,len,dst)
        def receive(src: Rep[Int]) = MPI_Irecv(data,off,len,src)
      }

      // A distributed character histogram, accumulating counts
      // for each node, exchanging, and merging so that the result
      // histogram is partitioned across nodes by key
      case class Histogram() {
        val size = 256
        val histogram = NewArray[Int](size) // store and send buffer
        for (i <- 0 until histogram.length)
          histogram(i) = 0

        val histogramR = NewArray[Int](size) // receive and traverse buffer

        val bucketLen = size / nprocs
        def bucket(i: Rep[Int]) = View(histogram, i * bucketLen, bucketLen)
        def bucketR(i: Rep[Int]) = View(histogramR, i * bucketLen, bucketLen)

        // accumulator api
        def apply(i: Rep[Char]) = new {
          val b = bucket(i.toInt % nprocs)
          def += (n: Rep[Int]) = {
            b(i.toInt / nprocs) = b(i.toInt / nprocs) + n
          }
        }

        // transfers
        def send() = {
          for (i <- 0 until nprocs) if (i != pid)
            bucket(i).send(i)
        }

        def receive() = {
          for (i <- 0 until nprocs) if (i != pid)
            bucketR(i).receive(i)
        }

        def merge() = {
          val b = bucket(pid)
          for (i <- (0 until nprocs)) if (i != pid)
            b += bucketR(i)
        }

        def sync() = MPI_Barrier()

        def exchange() = {
          send()
          receive()
          sync()
          merge()
        }

        // generator api
        def foreach(f: (Rep[Char], Rep[Int]) => Unit): Unit = {
          val b = bucket(pid)
          for (j <- 0 until bucketLen)
            f((pid + j * nprocs).toChar, b(j))
        }

      }

      val str = "My name is Ozymandias, King of Kings; Look on my Works, ye Mighty, and despair!"

      def snippet(arg: Rep[String]): Rep[Unit] = {
        val input = Input(str)
        val histogram = Histogram()

        input.foreach { c =>
          histogram(c) += 1
        }

        histogram.exchange()

        histogram.foreach { (c,n) =>
          if (n != 0) printf("%d: '%c' %d\n", pid, c, n)
        }
      }
    }
    val expected = snippet.str.groupBy(c => c).map { case (c,cs) => s": '$c' ${cs.length}" }.toSet
    val actual = lms.core.utils.captureOut(snippet.eval("ARG")).lines.map(s => s.substring(s.indexOf(':'))).toSet // drop pid, since we don't know many here
    assert { actual == expected }
    check("charcount_par", snippet.code, "c")
  }

/**
Second Step: Distributed Word Counts
------------------------------------

How to go from character counts to word counts? The major
difference is that we can no longer use dense fixed-size
arrays to represent histograms but need a sparse data structure
such as hash tables.

### Unstaged Reference Implementation

We provide an unstaged reference implementation below
that simulates multi-thread or multi-process execution.
This will serve as stepping stone for our actual MPI
implementation.
*/

  trait API {

    trait Scanner {
      def next(d: Char): String
      def hasNext: Boolean
    }

    trait Generator[T] {
      def foreach(f: T => Unit): Unit
    }

    trait Accumulator[T] {
      def +=(x: T): Unit
    }

    def StringScanner(data: String) = new Scanner {
      var pos = 0
      def next(d: Char) = {
        val start = pos
        while (pos < data.length && data(pos) != d)
          pos += 1
        try data.substring(start,pos)
        finally if (pos < data.length) pos += 1
      }
      def hasNext = pos < data.length
    }

    def WordGen(input: String) = new Generator[String] {
      val sc = StringScanner(input)
      def foreach(f: String => Unit): Unit = {
        while(sc.hasNext) {
          f(sc.next(' '))
        }
      }
    }

    case class HashPlusAcc() extends Accumulator[(String,Int)] with Generator[(String,Int)] {
      val hm = new mutable.HashMap[String,Int]
      def +=(x: (String,Int)): Unit = {
        hm(x._1) = hm.getOrElse(x._1, 0) + x._2
      }
      def foreach(f: ((String,Int)) => Unit): Unit = {
        hm.foreach(f)
      }
    }

    // ----

    val nThreads = 4
    var curThreadOpt: Option[Int] = None
    def curThread = curThreadOpt.get

    def par(f: => Unit): Unit = {
      assert(curThreadOpt.isEmpty)
      for (i <- 0 until nThreads) {
        curThreadOpt = Some(i)
        try f finally curThreadOpt = None
      }
    }

    case class WordGenWithOverhang(input: String) extends Generator[String] {
      val sc = StringScanner(input)
      var overhang = ""
      def skip() = sc.next(' ')
      def foreach(f: String => Unit): Unit = {
        while(sc.hasNext) {
          val w = sc.next(' ')
          // if at end, deal with overhang
          if (sc.pos == input.length && overhang != "") {
            if (input(sc.pos-1) == ' ') {
              f(w); f(overhang)
            } else {
              f(w + overhang)
            }
          } else {
            f(w)
          }
        }
      }
    }


    case class WordGenPar(input: Array[String]) extends Generator[String] {
      assert(input.length == nThreads)
      val sc = new Array[WordGenWithOverhang](nThreads)
      par { sc(curThread) = WordGenWithOverhang(input(curThread)) }

      par { if (curThread > 0) sc(curThread-1).overhang = sc(curThread).skip() }

      def foreach(f: String => Unit): Unit = {
        par { sc(curThread).foreach(f) }
      }
    }


    case class HashPlusAccPar() extends Accumulator[(String,Int)] with Generator[(String,Int)] {

      val hm = new Array[HashPlusAcc](nThreads)
      par { hm(curThread) = HashPlusAcc() }

      def +=(x: (String,Int)): Unit = {
        // shared mem could use CAS
        hm(x._1.hashCode % nThreads) += x
      }
      def foreach(f: ((String,Int)) => Unit): Unit = {
        par { hm(curThread).foreach(f) }
      }
    }


    case class HashPlusAccPartitioned(n: Int) extends Accumulator[(String,Int)] with Generator[(String,Int)] {

      val hm = new Array[HashPlusAcc](n)
      for (i <- 0 until n) { hm(i) = HashPlusAcc() }

      def +=(x: (String,Int)): Unit = {
        hm(x._1.hashCode % n) += x
      }
      def foreach(f: ((String,Int)) => Unit): Unit = {
        for (i <- 0 until n) hm(i).foreach(f)
      }
    }


    case class HashPlusAccDist() extends Accumulator[(String,Int)] with Generator[(String,Int)] {

      val hm = new Array[HashPlusAccPartitioned](nThreads) // index by cur thread, then target thread
      par { hm(curThread) = HashPlusAccPartitioned(nThreads) }

      def +=(x: (String,Int)): Unit = {
        hm(curThread) += x
      }
      def foreach(f: ((String,Int)) => Unit): Unit = {
        // exchange:
        // process i, send hm(i,j) to process j
        // process j accumulates
        par {
          // for all target, send(hm(curThread).hm(target), target)
          // for all source, received(hm1(curThread).hm(source), from source
          // accumulate all hm1(curThread).hm(source)
          // want to do this in a chunked, round-robin, way?
        }

        par {
          // only write to hm(curThread)(curThread)
          val hm1 = hm(curThread).hm(curThread)
          for (i <- 0 until nThreads) {
            if (i != curThread)
              hm(i).hm(curThread).foreach(x => hm1 += x)
          }
          hm1.foreach(f)
        }
      }
    }
  }


  test("wordcount_unstaged_seq") {
    val actual = lms.core.utils.captureOut(new API {
      val input = "foo bar baz foo bar foo foo foo boom bang boom boom yum"

      val wg = WordGen(input)
      val hm = HashPlusAcc()

      wg.foreach { w =>
        hm += (w, 1)
      }

      hm.foreach { case (w,c) =>
        println(s"$w ${w.hashCode % 4} $c")
      }
    })
    assert(actual ==
"""baz 3 1
boom 3 3
bang 0 1
yum 1 1
foo 2 5
bar 3 2
""")
  }

  test("wordcount_unstaged_par") {
    val actual = lms.core.utils.captureOut(new API {
      val input = "foo bar baz foo bar foo foo foo boom bang boom boom yum"
      val inputs = input.grouped((input.length - 1) / nThreads + 1).toArray

      println(inputs.deep)
      println(inputs.map(_.length).deep)

      val wg = WordGenPar(inputs)
      val hm = HashPlusAccDist()

      wg.foreach { w =>
        hm += (w, 1)
      }

      hm.foreach { case (w,c) =>
        println(s"$w ${w.hashCode % 4} $c")
      }
    })
    assert(actual ==
"""Array(foo bar baz fo, o bar foo foo , foo boom bang , boom boom yum)
Array(14, 14, 14, 13)
bang 0 1
yum 1 1
foo 2 5
baz 3 1
boom 3 3
bar 3 2
""")
  }

/**
### Staged and Distributed Implementation

TODO / Exercise: complete the implementation by writing an mmap-based
Scanner (assuming each cluster node has access to a common file system)
and adapting the hash table implementation used for `GroupBy` in the
query tutorial to the distributed setting with communication along the
lines used in the character histogram above.
*/


}