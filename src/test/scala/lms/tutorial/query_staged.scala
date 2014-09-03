package scala.lms.tutorial

import scala.virtualization.lms.common._

object query_staged {
trait QueryCompiler extends Dsl with StagedQueryProcessor
with ScannerBase {
  override def version = "query_staged"

  // low-level processing
  type RField = Rep[String]
  type Fields = Vector[RField]

  case class Record(fields: Fields, schema: Schema) {
    def apply(key: String): RField = fields(schema indexOf key)
    def apply(keys: Schema): Fields = keys.map(this apply _)
  }

  def processCSV(filename: Rep[String], schema: Schema, fieldDelimiter: Char, externalSchema: Boolean)(yld: Record => Rep[Unit]): Rep[Unit] = {
    val s = newScanner(filename)
    val last = schema.last
    def nextRecord = Record(schema.map{x => s.next(if (x==last) '\n' else fieldDelimiter)}, schema)
    if (!externalSchema) {
      // the right thing would be to dynamically re-check the schema,
      // but it clutters the generated code
      // schema.foreach(f => if (s.next != f) println("ERROR: schema mismatch"))
      nextRecord // ignore csv header
    }
    while (s.hasNext) yld(nextRecord)
    s.close
  }

  def printSchema(schema: Schema) = println(schema.mkString(defaultFieldDelimiter.toString))

  def printFields(fields: Fields) = printf(fields.map{_ => "%s"}.mkString("\"", defaultFieldDelimiter.toString, "\\n\""), fields: _*)

  def fieldsEqual(a: Fields, b: Fields) = (a zip b).foldLeft(unit(true)) { (a,b) => a && b._1 == b._2 }

  def fieldsHash(a: Fields) = a.foldLeft(unit(0L)) { _ * 41L + _.HashCode }

  def evalPred(p: Predicate)(rec: Record): Rep[Boolean] = p match {
    case Eq(a1, a2) => evalRef(a1)(rec) == evalRef(a2)(rec)
  }

  def evalRef(r: Ref)(rec: Record): RField = r match {
    case Field(name) => rec(name)
    case Value(x) => x.toString
  }

  def resultSchema(o: Operator): Schema = o match {
    case Scan(_, schema, _, _)   => schema
    case Filter(pred, parent)    => resultSchema(parent)
    case Project(schema, _, _)   => schema
    case Join(left, right)       => resultSchema(left) ++ resultSchema(right)
    case Group(keys, agg, parent)=> keys ++ agg
    case HashJoin(left, right)   => resultSchema(left) ++ resultSchema(right)
    case PrintCSV(parent)        => Schema()
  }

  def execOp(o: Operator)(yld: Record => Rep[Unit]): Rep[Unit] = o match {
    case Scan(filename, schema, fieldDelimiter, externalSchema) =>
      processCSV(tableFor(filename), schema, fieldDelimiter, externalSchema)(yld)
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
    case Group(keys, agg, parent) =>
      val hm = new HashMapAgg(keys, agg)
      execOp(parent) { rec =>
        hm += (rec(keys), rec(agg))
      }
      hm foreach { (k,a) =>
        yld(Record(k ++ a, keys ++ agg))
      }
    case HashJoin(left, right) =>
      val keys = resultSchema(left) intersect resultSchema(right)
      val hm = new HashMapBuffer(keys, resultSchema(left))
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
  def execQuery(q: Operator): Unit = execOp(q) { _ => }

  // data structure implementations

  class HashMapAgg(keySchema: Schema, schema: Schema) {

    // TODO: finish
    def +=(k: Fields, v: Fields) = ???

    def foreach(f: (Fields,Fields) => Rep[Unit]): Rep[Unit] = ???
  }

  class HashMapBuffer(keySchema: Schema, schema: Schema) {

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
    val buf = schema.map(f => NewArray[String](dataSize)) // array type?
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
}
