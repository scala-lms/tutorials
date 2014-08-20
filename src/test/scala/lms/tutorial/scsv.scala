package scala.lms.tutorial

import org.scalatest.FunSuite

trait StagedCSV extends Dsl with ScannerBase {

  type Schema = Vector[String]
  def Schema(schema: String*): Schema = schema.toVector
  type Fields = Vector[Rep[String]]

  case class Record(fields: Fields, schema: Schema) {
    def apply(key: String): Rep[String] = fields(schema indexOf key)
  }

  sealed abstract class Operator
  case class Scan(filename: Rep[String], schema: Schema) extends Operator
  case class Project(schema: Schema, parent: Operator) extends Operator
  case class PrintCSV(parent: Operator) extends Operator

  def execOp(o: Operator)(yld: Record => Rep[Unit]): Rep[Unit] = o match {
    case Scan(filename, schema) =>
      val s = newScanner(filename)
      def nextRecord = Record(schema.map{_ => s.next}, schema)
      nextRecord // ignore csv header
      while (s.hasNext) yld(nextRecord)
    case Project(schema, parent) =>
      execOp(parent) { rec =>
        yld(Record(schema.map(k => rec(k)), schema))
      }
    case PrintCSV(parent) =>
      def pretty(xs: List[Rep[String]]): Rep[String] = xs match {
        case Nil => ""
        case x::Nil => x
        case x::xs => x+","+pretty(xs)
      }
      execOp(parent) { rec =>
        println(pretty(rec.fields.toList))
      }
  }
  def execQuery(q: Operator): Rep[Unit] = execOp(q){ _ => }
}

abstract class StagedQuery extends DslDriver[String,Unit] with StagedCSV with ScannerExp { q =>
  override val codegen = new DslGen with ScalaGenScanner {
    val IR: q.type = q
  }
  override def snippet(fn: Rep[String]): Rep[Unit] = execQuery(query(fn))
  def query(fn: Rep[String]): Operator
}

class StagedCSVTest extends TutorialFunSuite {
  val under = "query_"

  def testquery(name: String, csv: String, query: StagedQuery) {
    test(name) {
      check(name, query.code)
      checkOut(name, "csv", query.eval("src/data/" + csv))
    }
  }

  testquery("t1", "t.csv", new StagedQuery {
    def query(fn: Rep[String]) =
      PrintCSV(
        Scan(fn, Schema("Name", "Value", "Flag"))
      )
  })

  testquery("t2", "t.csv", new StagedQuery {
    def query(fn: Rep[String]) =
      PrintCSV(Project(Schema("Name"),
        Scan(fn, Schema("Name", "Value", "Flag"))
      ))
  })
}
