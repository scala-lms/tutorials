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

trait SQLParser extends QueryAST {
  import scala.util.parsing.combinator._
  object Grammar extends JavaTokenParsers with PackratParsers {
    def fieldIdent: Parser[String] = """[\w\#]+""".r
    def tableIdent: Parser[String] = """[\w_\-/\.]+""".r
    def fieldList: Parser[Schema]  = repsep(fieldIdent,",") ^^ { fs => Schema(fs:_*) }

    def ref: Parser[Ref] = fieldIdent ^^ Field | """'\w*'""".r ^^ (s => Value(s.drop(1).dropRight(1)))
    def predicate: Parser[Predicate] = ref ~ "=" ~ ref ^^ { case a ~ _ ~ b => Eq(a,b) }

    def fromClause: Parser[Operator] = "from" ~> tableIdent ^^ { table => Scan(table) }
    def selectClause: Parser[Operator=>Operator] = "select" ~> ("*" ^^ { _ => (op:Operator) => op } | fieldList ^^ { fs => Project(fs,fs,_:Operator) })
    def whereClause: Parser[Operator=>Operator] = opt("where" ~> predicate ^^ { p => Filter(p, _:Operator) }) ^^ { _.getOrElse(op => op)}

    def stm: Parser[Operator] = selectClause ~ fromClause ~ whereClause ^^ { case p ~ s ~ f => p(f(s)) }
  }
  def parseSql(input: String) = Grammar.parseAll(Grammar.stm, input).get // cleaner error reporting?
}

trait QueryAST {
  val defaultFieldDelimiter = ','
  type Schema
  def Schema(schema: String*): Schema
  def loadSchemaFor(tableName: String): Schema
  def externalSchemaFor(tableName: String): Option[Schema] = None
  def fieldDelimiterFor(tableName: String): Option[Char] = None

  def Scan(tableName: String): Scan = {
    val (schema, externalSchema) = externalSchemaFor(tableName) match {
      case Some(schema) => (schema, true)
      case None => (loadSchemaFor(tableName), false)
    }
    val fieldDelimiter = fieldDelimiterFor(tableName) match {
      case Some(d) => d
      case None => defaultFieldDelimiter
    }
    Scan(tableName, schema, fieldDelimiter, externalSchema)
  }

  sealed abstract class Operator
  case class Scan(tableName: String, schema: Schema, fieldDelimiter: Char, externalSchema: Boolean) extends Operator
  case class PrintCSV(parent: Operator) extends Operator
  case class Project(schema: Schema, schema2: Schema, parent: Operator) extends Operator
  case class Filter(pred: Predicate, parent: Operator) extends Operator
  case class Join(parent1: Operator, parent2: Operator) extends Operator
  case class Group(keys: Schema, agg: Schema, parent: Operator) extends Operator
  case class HashJoin(parent1: Operator, parent2: Operator) extends Operator

  // for filtering
  sealed abstract class Predicate
  case class Eq(a: Ref, b: Ref) extends Predicate

  sealed abstract class Ref
  case class Field(name: String) extends Ref
  case class Value(x: String) extends Ref
}

trait StagedCSV extends Dsl with QueryAST with ScannerBase {
  def defaultFilenameFor(tableName: String): String

  override type Schema = Vector[String]
  def Schema(schema: String*): Schema = schema.toVector

  type Table = Rep[String] // dynamic filename
  def tableFor(tableName: String): Table = unit(defaultFilenameFor(tableName))

  // low-level processing
  type RField = Rep[String]
  type Fields = Vector[RField]

  case class Record(fields: Fields, schema: Schema) {
    def apply(key: String): RField = fields(schema indexOf key)
    def apply(keys: Schema): Fields = keys.map(this apply _)
  }

  override def loadSchemaFor(tableName: String) =
    loadSchema(defaultFilenameFor(tableName))
  def loadSchema(filename: String): Schema = {
    val s = new Scanner(filename)
    val schema = Schema(s.next('\n').split(defaultFieldDelimiter): _*)
    s.close
    schema
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

  def evalRef(r: Ref)(rec: Record): Rep[Any] = r match {
    case Field(name) => rec(name)
    case Value(x) => x
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
  def execQuery(q: Operator): Rep[Unit] = execOp(q) { _ => }

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

class StagedCSVTest extends TutorialFunSuite {
  val under = "query_"

  def testquery(name: String, query: String = "") {
    test(name) {
      val snippet = new DslDriver[String,Unit] with SQLParser with StagedCSV with ScannerExp with ExpectedASTs { q =>
        override val codegen = new DslGen with ScalaGenScanner {
          val IR: q.type = q
        }
        var defaultTable: Table = _
        def parsedQuery: Operator = if (query.isEmpty) expectedAstForTest(name) else parseSql(query)
        override def snippet(fn: Rep[String]): Rep[Unit] = {
          defaultTable = fn
          execQuery(PrintCSV(parsedQuery))
        }
        override def defaultFilenameFor(tableName: String) = dataFilePath(tableName+".csv")
        // this is special-cased to run legacy queries as is
        // TODO: generalize once it works
        override def tableFor(tableName: String) = tableName match {
          case "t1gram" => defaultTable // dynamic
          case _ => super.tableFor(tableName) // constant
        }
        override def externalSchemaFor(tableName: String) =
          if (tableName.contains("gram")) Some(Schema("Phrase", "Year", "MatchCount", "VolumeCount"))
          else super.externalSchemaFor(tableName)
        override def fieldDelimiterFor(tableName: String) =
          if (tableName.contains("gram")) Some('\t')
          else super.fieldDelimiterFor(tableName)
      }
      assert(snippet.expectedAstForTest(name)==snippet.parsedQuery)
      check(name, snippet.code)
      snippet.precompile
      checkOut(name, "csv", snippet.eval(snippet.defaultFilenameFor(if (query.contains("gram")) "t1gram" else "t")))
    }
  }

  trait ExpectedASTs extends QueryAST {
    val expectedAstForTest = Map(
      "t1" -> Scan("t"),
      "t2" -> Project(Schema("Name"), Schema("Name"), Scan("t")),
      "t3" -> Project(Schema("Name"), Schema("Name"),
                      Filter(Eq(Field("Flag"), Value("yes")),
                             Scan("t"))),
      "t4" -> Join(Scan("t"),
                   Project(Schema("Name1"), Schema("Name"), Scan("t"))),
      "t5" -> Join(Scan("t"),
                   Project(Schema("Name"), Schema("Name"), Scan("t"))),

      "t1gram1" -> Scan("t1gram"),
      "t1gram2" -> Filter(Eq(Field("Phrase"), Value("Auswanderung")), Scan("t1gram"))
    )
  }

  testquery("t1", "select * from t")
  testquery("t2", "select Name from t")
  testquery("t3", "select Name from t where Flag='yes'")
  testquery("t4")
  testquery("t5")

  testquery("t1gram1", "select * from t1gram")
  testquery("t1gram2", "select * from t1gram where Phrase='Auswanderung'")
}
