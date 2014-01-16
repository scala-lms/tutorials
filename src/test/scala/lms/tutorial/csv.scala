package scala.lms.tutorial

import scala.virtualization.lms.common._
import scala.reflect.SourceContext

// sbt ~test-only *CSV*

class CSVTest extends TutorialFunSuite {
  val under = "dslapi"

/*  playground for live demo */

  test("csv-live") {

    def FileReader(file: String) = 
"""Name,Value,Flag
A,7,no
B,2,yes""".split("\n").iterator

    case class Record(fields: Array[String], schema: Array[String]) {
      def apply(key: String) = fields(schema indexOf key)
    }

    def CSV(file: String) = new Object {
      def foreach(yld: Record => Unit) = {
        val lines = FileReader(file)
        val schema = lines.next.split(",")
        while (lines.hasNext) {
          val fields = lines.next().split(",")
          yld(new Record(fields,schema))
        }
      }
    }
    
    for (record <- CSV("data.txt")) { 
      if (record("Flag") == "yes")
        println(record("Name"))
    }


    abstract class Operator
    case class Scan(file: String) extends Operator
    case class Filter(pred: Predicate, parent: Operator) extends Operator
    case class Project(fields: Array[String], parent: Operator) extends Operator
    case class Print(parent: Operator) extends Operator

    abstract class Predicate
    case class Eq(a: Ref, b: Ref) extends Predicate

    abstract class Ref
    case class Field(name: String) extends Ref
    case class Const(x: Any) extends Ref

    def exec(o: Operator)(yld: Record => Unit): Unit = o match {
      case Scan(file) => 
        CSV(file).foreach(yld)
      case Filter(pred, parent) => 
        exec(parent) { rec => if (evalp(pred)(rec)) yld(rec) }
      case Project(fields, parent) => 
        exec(parent) { rec => yld(new Record(fields map (k => rec(k)), fields)) }
      case Print(parent) =>
        exec(parent) { rec => println(rec.fields mkString ",") }
    }
    def evalp(p: Predicate)(rec: Record): Boolean = p match {
      case Eq(a1,a2) => eval(a1)(rec) == eval(a2)(rec)
    }
    def eval(p: Ref)(rec: Record): Any = p match {
      case Field(name) => rec(name)
      case Const(x) => x
    }

    // SELECT Name FROM data.txt WHERE Flag == 'yes'

    val q = Print(
      Project(Array("Name"),
        Filter(Eq(Field("Flag"),Const("yes")),
          Scan("data.txt"))))

    exec(q) { record =>
      if (record("Flag") == "yes")
        println(record("Name"))
    }

    this.exec("csv-live", "done")
  }




  /*
    val snippet = new DslDriver[Array[Int],Array[Int]] {
      def snippet(v: Rep[Array[Int]]) = {
        println("hello")
        v
      }
    }
    exec("csv-live", snippet.code)
  */



/*
  DEMO:
  1) add compile snippet
  ...
*/
}