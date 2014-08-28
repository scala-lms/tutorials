/**
A SQL Query Compiler
====================

Abstraction without regret for efficient data processing.

Outline:
<div id="tableofcontents"></div>

*/

package scala.lms.tutorial

import scala.virtualization.lms.common._
import org.scalatest.FunSuite

trait StagedCSV extends Dsl with ScannerBase with UncheckedOps {

  // low-level processing

  type Schema = Vector[String]
  def Schema(schema: String*): Schema = schema.toVector
  type Fields = Vector[Rep[Any]]

  case class Record(fields: Fields, schema: Schema) {
    def apply(key: String): Rep[Any] = fields(schema indexOf key)
    def apply(keys: Schema): Fields = keys.map(this apply _)
  }

  def loadSchema(filename: String): Schema = {
    val s = new Scanner(filename)
    var schema: Schema = Schema() // force present-stage var
    do (schema :+= s.next) while (s.hasNextInLine)
    s.close
    schema
  }

  def processCSV(filename: Rep[String], schema: Schema)(yld: Record => Rep[Unit]): Rep[Unit] = {
    val s = newScanner(filename)
    def nextRecord = Record(schema.map{_ => s.next}, schema)
    // the right thing would be to check the schema, but it clutters the generated code
    // schema.foreach(f => if (s.next != f) println("ERROR: schema mismatch"))
    nextRecord // ignore csv header
    while (s.hasNext) yld(nextRecord)
  }

  def printSchema(schema: Schema) = println(schema.mkString(","))

  def printFields(fields: Fields) = {
    def pretty(xs: List[Rep[Any]]): Rep[String] = xs match {
      case Nil => ""
      case x::Nil => x.ToString
      case x::xs => x+","+pretty(xs)
    }
    println(pretty(fields.toList))
  }

  def fieldsEqual(a: Fields, b: Fields) = (a zip b).foldLeft(unit(true)) { (a,b) => a && b._1 == b._2 }

  def fieldsHash(a: Fields) = a.foldLeft(unit(0L)) { _ * 41L + _.HashCode }

  def infix_HashCode(a: Rep[Any]) = unchecked[Long](a,".##") // TODO: clean up / add in LMS
  def infix_ToString(a: Rep[Any]) = if (a.asInstanceOf[{def tp: Manifest[Any]}].tp == manifest[String]) a.asInstanceOf[Rep[String]] 
                                    else unchecked[String](a,".toString") // TODO: clean up / add in LMS

  // query operators

  sealed abstract class Operator
  // the definition of operators in order of incremental development
  def Scan(filename: String) = new Scan(filename, loadSchema(filename))
  case class Scan(filename: String, schema: Schema) extends Operator
  case class PrintCSV(parent: Operator) extends Operator
  case class Project(schema: Schema, schema2: Schema, parent: Operator) extends Operator
  case class Filter(pred: Predicate, parent: Operator) extends Operator
  case class Join(parent1: Operator, parent2: Operator) extends Operator
  case class HashJoin(parent1: Operator, parent2: Operator) extends Operator

  // these utilities are (for now) only needed for filtering
  sealed abstract class Predicate
  case class Eq(a: Ref, b: Ref) extends Predicate

  sealed abstract class Ref
  case class Field(name: String) extends Ref
  case class Value(x: String) extends Ref

  def evalPred(p: Predicate)(rec: Record): Rep[Boolean] = p match {
    case Eq(a1, a2) => evalRef(a1)(rec) == evalRef(a2)(rec)
  }

  def evalRef(r: Ref)(rec: Record): Rep[Any] = r match {
    case Field(name) => rec(name)
    case Value(x) => x
  }
  // end of utilities for filtering

  def resultSchema(o: Operator): Schema = o match {
    case Scan(filename, schema)  => schema
    case Filter(pred, parent)    => resultSchema(parent)
    case Project(schema, _, _)   => schema
    case Join(left, right)       => resultSchema(left) ++ resultSchema(right)
    case HashJoin(left, right)   => resultSchema(left) ++ resultSchema(right)
    case PrintCSV(parent)        => Schema()
  }

  def execOp(o: Operator)(yld: Record => Rep[Unit]): Rep[Unit] = o match {
    case Scan(filename, schema) =>
      processCSV(filename, schema)(yld)
    case Filter(pred, parent) =>
      execOp(parent) { rec => if (evalPred(pred)(rec)) yld(rec) }
    case Project(newSchema, parentSchema, parent) =>
      execOp(parent) { rec => yld(Record(rec(parentSchema), newSchema)) }
    case Join(left, right) =>
      execOp(left) { rec1 =>
        execOp(right) { rec2 =>
          val keys = rec1.schema intersect rec2.schema
          if (fieldsEqual(rec1(keys), rec2(keys)))
            yld(Record(rec1.fields ++ rec2.fields, rec1.schema ++ rec2.schema))
        }
      }
    case HashJoin(left, right) =>
      val keys = resultSchema(left) intersect resultSchema(right)
      val hm = new HashMap(keys, resultSchema(left))
      execOp(left) { rec1 =>
        hm += (rec1(keys), rec1.fields)
      }
      execOp(right) { rec2 =>
        hm(rec2(keys)) foreach { rec1 =>
          yld(Record(rec1.fields ++ rec2.fields, rec1.schema ++ rec2.schema))
        }
      }
    case PrintCSV(parent) =>
      val schema = resultSchema(parent)
      printSchema(schema)
      execOp(parent) { rec => printFields(rec.fields) }
  }
  def execQuery(q: Operator): Rep[Unit] = execOp(q) { _ => }

  // data structure implementations

  class HashMap(keySchema: Schema, schema: Schema) {

    // FIXME: hard coded data sizes
    val hashSize = (1 << 8)
    val bucketSize = (1 << 8)
    val dataSize = hashSize * bucketSize

    // TODO: implement resizing and/or initial size heuristic based on file size

    val data = new ArrayBuffer(dataSize, schema)
    val dataCount = var_new(0)

    val buckets = NewArray[Int](dataSize)
    val bucketKey = new ArrayBuffer(hashSize, keySchema)
    val bucketCounts = NewArray[Int](hashSize)

    for (i <- 0 until hashSize) {
        bucketCounts(i) = 0
    }

    val hashMask = hashSize - 1

    def lookup(put: Boolean)(k: Fields): Rep[Int] = comment[Int]("hash_lookup") {
        val h = fieldsHash(k).toInt
        var bucket = h & hashMask
        while (bucketCounts(bucket) != 0 && !fieldsEqual(bucketKey(bucket),k)) {
          bucket = (bucket + 1) & hashMask
        }
        if (put) bucketKey(bucket) = k
        bucket: Rep[Int]
    }

    def +=(k: Fields, v: Fields) = {
      val dataPos = dataCount: Rep[Int] // force read
      data(dataPos) = v
      dataCount += 1

      val bucket = lookup(put = true)(k)
      val bucketPos = bucketCounts(bucket)
      buckets(bucket * bucketSize + bucketPos) = dataPos
      bucketCounts(bucket) = bucketPos + 1
    }

    def apply(k: Fields) = new {
      def foreach(f: Record => Rep[Unit]): Rep[Unit] = {
        val bucket = lookup(put = false)(k)

        val bucketLen = bucketCounts(bucket)
        val bucketStart = bucket * bucketSize

        for (i <- bucketStart until (bucketStart + bucketLen)) {
          f(Record(data(buckets(i)),schema))
        }
      }
    }
  }

  class ArrayBuffer(dataSize: Int, schema: Schema) {
    val buf = schema.map(f => NewArray[Any](dataSize))
    var len = 0
    def +=(x: Fields) = {
      this(len) = x
      len += 1
    }
    def update(i: Rep[Int], x: Fields) = {
      (buf,x).zipped.foreach((b,x) => b(i) = x)
    }
    def apply(i: Rep[Int]) = {
      buf.map(b => b(i))
    }
  }


}

abstract class StagedQuery extends DslDriver[String,Unit] with StagedCSV with ScannerExp with UncheckedOpsExp 
  with VariablesExpOpt with IfThenElseExpOpt { q =>
  override val codegen = new DslGen with ScalaGenScanner with ScalaGenUncheckedOps {
    val IR: q.type = q
  }
  override def snippet(fn: Rep[String]): Rep[Unit] = execQuery(query)
  def filePath(csv: String) = "src/data/" + csv
  def query: Operator

  override def isPrimitiveType[T](m: Manifest[T]) = (m == manifest[String]) || super.isPrimitiveType(m) // TODO: should this be in LMS?
}

class StagedCSVTest extends TutorialFunSuite {
  val under = "query_"

  def testquery(name: String, query: StagedQuery) {
    test(name) {
      check(name, query.code)
      query.precompile
      checkOut(name, "csv", query.eval(""))
    }
  }

  testquery("t1", new StagedQuery {
    def query =
      PrintCSV(
        Scan(filePath("t.csv"))
      )
  })

  testquery("t2", new StagedQuery {
    def query =
      PrintCSV(Project(Schema("Name"), Schema("Name"),
        Scan(filePath("t.csv"))
      ))
  })

  testquery("t3", new StagedQuery {
    def query =
      PrintCSV(Project(Schema("Name"), Schema("Name"),
        Filter(Eq(Field("Flag"), Value("yes")),
          Scan(filePath("t.csv"))
      )))
  })

  testquery("t4", new StagedQuery {
    def query =
      PrintCSV(
        Join(
          Scan(filePath("t.csv")),
          Project(Schema("Name1"), Schema("Name"), Scan(filePath("t.csv")))
      ))
  })

  testquery("t5", new StagedQuery {
    def query =
      PrintCSV(
        Join(
          Scan(filePath("t.csv")),
          Project(Schema("Name"), Schema("Name"), Scan(filePath("t.csv")))
      ))
  })

  testquery("t4h", new StagedQuery {
    def query =
      PrintCSV(
        HashJoin(
          Scan(filePath("t.csv")),
          Project(Schema("Name1"), Schema("Name"), Scan(filePath("t.csv")))
      ))
  })

  testquery("t5h", new StagedQuery {
    def query =
      PrintCSV(
        HashJoin(
          Scan(filePath("t.csv")),
          Project(Schema("Name"), Schema("Name"), Scan(filePath("t.csv")))
      ))
  })

}
