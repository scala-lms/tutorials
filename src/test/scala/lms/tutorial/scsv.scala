package scala.lms.tutorial

import org.scalatest.FunSuite

trait StagedCSV extends Dsl with ScannerBase {

  type Schema = Vector[String]
  def Schema(schema: String*): Schema = schema.toVector
  type Fields = Vector[Rep[String]]

  case class Record(fields: Fields, schema: Schema) {
    def apply(key: String): Rep[String] = fields(schema indexOf key)
  }

  def loadSchema(filename: String): Schema = {
    val s = new Scanner(filename)
    var schema: Schema = Schema() // force present-stage var
    do (schema :+= s.next) while (s.hasNextInLine)
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
    def pretty(xs: List[Rep[String]]): Rep[String] = xs match {
      case Nil => ""
      case x::Nil => x
      case x::xs => x+","+pretty(xs)
    }
    println(pretty(fields.toList))
  }


  sealed abstract class Operator
  // the definition of operators in order of incremental development
  def Scan(filename: String) = new Scan(filename, loadSchema(filename))
  case class Scan(filename: String, schema: Schema) extends Operator
  case class PrintCSV(parent: Operator) extends Operator
  case class Project(schema: Schema, schema2: Schema, parent: Operator) extends Operator
  case class Filter(pred: Predicate, parent: Operator) extends Operator
  case class Join(parent1: Operator, parent2: Operator) extends Operator // TODO: natural join? explicit join condition? for now it's just a cartesian product

  // these utilities are (for now) only needed for filtering
  sealed abstract class Predicate
  case class Eq(a: Ref, b: Ref) extends Predicate

  sealed abstract class Ref
  case class Field(name: String) extends Ref
  case class Value(x: String) extends Ref

  def evalPred(p: Predicate)(rec: Record): Rep[Boolean] = p match {
    case Eq(a1, a2) => evalRef(a1)(rec) == evalRef(a2)(rec)
  }

  def evalRef(r: Ref)(rec: Record): Rep[String] = r match {
    case Field(name) => rec(name)
    case Value(x) => x
  }
  // end of utilities for filtering

  def resultSchema(o: Operator): Schema = o match {
    case Scan(filename, schema)  => schema
    case Filter(pred, parent)    => resultSchema(parent)
    case Project(schema, _, _)   => schema
    case Join(left, right)       => resultSchema(left) ++ resultSchema(right) 
    case PrintCSV(parent)        => Schema()
  }

  def execOp(o: Operator)(yld: Record => Rep[Unit]): Rep[Unit] = o match {
    case Scan(filename, schema) =>
      processCSV(filename, schema)(yld)
    case Filter(pred, parent) =>
      execOp(parent) { rec => if (evalPred(pred)(rec)) yld(rec) }
    case Project(schema, schema2, parent) =>
      execOp(parent) { rec => yld(Record(schema2.map(k => rec(k)), schema)) }
    case Join(left, right) =>
      execOp(left) { rec1 => 
        execOp(right) { rec2 =>
          yld(Record(rec1.fields ++ rec2.fields, rec1.schema ++ rec2.schema))
        }
      }
    case PrintCSV(parent) =>
      val schema = resultSchema(parent)
      printSchema(schema)
      execOp(parent) { rec => printFields(rec.fields) }
  }
  def execQuery(q: Operator): Rep[Unit] = execOp(q) { _ => }
}

abstract class StagedQuery extends DslDriver[String,Unit] with StagedCSV with ScannerExp { q =>
  override val codegen = new DslGen with ScalaGenScanner {
    val IR: q.type = q
  }
  override def snippet(fn: Rep[String]): Rep[Unit] = execQuery(query)
  def filePath(csv: String) = "src/data/" + csv
  def query: Operator
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
  
}
