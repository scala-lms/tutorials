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

/**
Parser
------

We start with a parser for a SQL(-like) language.

*/

trait SQLParser extends QueryAST {
  import scala.util.parsing.combinator._
  object Grammar extends JavaTokenParsers with PackratParsers {
    def fieldIdent: Parser[String] = """[\w\#]+""".r
    def tableIdent: Parser[String] = """[\w_\-/\.]+""".r
    def fieldList: Parser[Schema]  = repsep(fieldIdent,",") ^^ { fs => Schema(fs:_*) }

    def predicate: Parser[Predicate] = ref ~ "=" ~ ref ^^ { case a ~ _ ~ b => Eq(a,b) }
    def ref: Parser[Ref] = fieldIdent ^^ Field | """'\w*'""".r ^^ (s => Value(s.drop(1).dropRight(1))) |       
          """[0-9]+""".r ^^ (s => Value(s.toInt))
    def fromClause: Parser[Operator] = 
      "from" ~> tableIdent ~ opt("schema" ~> fieldList) ~ opt("delim" ~> ("""\t""" ^^ (_ => '\t') | """.""".r ^^ (_.head))) ^^ { 
        case table ~ schema ~ delim => Scan(table, schema, delim)
      }
    def selectClause: Parser[Operator=>Operator] = 
      "select" ~> ("*" ^^ { _ => (op:Operator) => op } | fieldList ^^ { fs => Project(fs,fs,_:Operator) })
    def whereClause: Parser[Operator=>Operator] = 
      opt("where" ~> predicate ^^ { p => Filter(p, _:Operator) }) ^^ { _.getOrElse(op => op)}

    def stm: Parser[Operator] = selectClause ~ fromClause ~ whereClause ^^ { case p ~ s ~ f => p(f(s)) }
  }
  def parseSql(input: String) = Grammar.parseAll(Grammar.stm, input).get // cleaner error reporting?
}

/**
AST
---

The parser takes a string and convers it to an AST.
*/
trait QueryAST {
  def tableFor(s: Table) = s // remove

  type Schema = Vector[String]
  def Schema(schema: String*): Schema = schema.toVector
  type Table
  def Scan(tableName: String): Scan = Scan(tableName, None, None)
  def Scan(tableName: String, schema: Option[Schema], delim: Option[Char]): Scan // defined in QueryProcessor

  sealed abstract class Operator
  case class Scan(tableName: Table, schema: Schema, fieldDelimiter: Char, externalSchema: Boolean) extends Operator
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
  case class Value(x: Any) extends Ref
}

/**
Iterative Development of a Query Processor
------------------------------------------

We develop our SQL engine in multiple steps. Each steps leads to a
working processor, and each successive step either adds a feature or
optimization. We define a common interface for each processor.

*/
trait QueryProcessor extends QueryAST {
  def version: String
  val defaultFieldDelimiter = ','

  def filePath(table: String): String = table
  def dynamicFilePath(table: String): Table

  def Scan(tableName: String, schema: Option[Schema], delim: Option[Char]): Scan = {
    val dfile = dynamicFilePath(tableName)
    val (schema1, externalSchema) = schema.map(s=>(s,true)).getOrElse((loadSchema(filePath(tableName)),false))
    Scan(dfile, schema1, delim.getOrElse(defaultFieldDelimiter), externalSchema)
  }

  def loadSchema(filename: String): Schema = {
    val s = new Scanner(filename)
    val schema = Schema(s.next('\n').split(defaultFieldDelimiter): _*)
    s.close
    schema
  }

  def execQuery(q: Operator): Unit
}

/**
### a (plain) Query Interpreter

We start with a plain query processor: an interpreter.

- [query_unstaged.scala](query_unstaged.html)

*/
trait PlainQueryProcessor extends QueryProcessor {
  type Table = String
  def dynamicFilePath(table: String) = filePath(table)
}

/**

### a Staged Query Interpreter (= Compiler)

- [query_staged0](query_staged0.scala)

- [query_staged](query_staged.scala)

- [query_optc](query_optc.scala)

*/
trait StagedQueryProcessor extends QueryProcessor with Dsl {
  type Table = Rep[String] // dynamic filename
  override def filePath(table: String) = if (table == "?") throw new Exception("file path for table ? not available") else super.filePath(table)
  def dynamicFileName: Table
  def dynamicFilePath(table: String) = if (table == "?") dynamicFileName else unit(filePath(table))
}

/**

Unit Tests
-----

*/
class QueryTest extends TutorialFunSuite {
  val under = "query_"

  trait TestDriver extends SQLParser with QueryProcessor with ExpectedASTs {
    def runtest: Unit
    override def filePath(table: String) = {
      "src/data/" + table
    }

    def name: String
    def query: String
    def parsedQuery: Operator = if (query.isEmpty) expectedAstForTest(name) else parseSql(query)
  }

  trait PlainTestDriver extends TestDriver with PlainQueryProcessor {
    def eval(fn: Table): Unit = execQuery(PrintCSV(parsedQuery))
  }

  trait StagedTestDriver extends TestDriver with StagedQueryProcessor {
    var dynamicFileName: Table = _
    def snippet(fn: Table): Rep[Unit] = {
      dynamicFileName = fn
      execQuery(PrintCSV(parsedQuery))
    }
  }

  abstract class ScalaPlainQueryDriver(val name: String, val query: String) extends PlainTestDriver with QueryProcessor { q =>
    override def runtest: Unit = {
      test(version+" "+name) {
        assert(expectedAstForTest(name)==parsedQuery)
        checkOut(name, "csv", eval("DUMMY"))
      }
    }
  }

  abstract class ScalaStagedQueryDriver(val name: String, val query: String) extends DslDriver[String,Unit] with StagedTestDriver with StagedQueryProcessor with ScannerExp { q =>
    override val codegen = new DslGen with ScalaGenScanner {
      val IR: q.type = q
    }
    override def runtest: Unit = {
      if (version == "query_staged0" && query.isEmpty) return ()
      test(version+" "+name) {
        assert(expectedAstForTest(name)==parsedQuery)
        /* FIXME: group by currently not supported in staged Scala version*/
        if (!parsedQuery.toString.contains("Group(")) {
          check(name, code)
          precompile
          checkOut(name, "csv", eval("DUMMY"))
        }
      }
    }
  }

  abstract class CStagedQueryDriver(val name: String, val query: String) extends DslDriverC[String,Unit] with StagedTestDriver with StagedQueryProcessor with ScannerLowerExp { q =>
    override val codegen = new DslGenC with CGenScannerLower {
      val IR: q.type = q
    }
    override def runtest: Unit = {
      test(version+" "+name) {
        assert(expectedAstForTest(name)==parsedQuery)
        check(name, code, "c")
        //precompile
        checkOut(name, "csv", eval("DUMMY"))
      }
    }
  }

  def testquery(name: String, query: String = "") {
    val drivers: List[TestDriver] =
      List(
        new ScalaPlainQueryDriver(name, query) with query_unstaged.QueryInterpreter,
        new ScalaStagedQueryDriver(name, query) with query_staged0.QueryCompiler,
        new ScalaStagedQueryDriver(name, query) with query_staged.QueryCompiler,
        new CStagedQueryDriver(name, query) with query_optc.QueryCompiler {
          // FIXME: hack so i don't need to replace Value -> #Value in all the files right now
          override def isNumericCol(s: String) = s == "Value" || super.isNumericCol(s)
        }
      )
    drivers.foreach(_.runtest)
  }

  // NOTE: we can use "select * from ?" to use dynamic file names (not used here right now)

  trait ExpectedASTs extends QueryAST {
    val scan_t = Scan("t.csv")
    val scan_t1gram = Scan("t1gram.csv",Some(Schema("Phrase", "Year", "MatchCount", "VolumeCount")),Some('\t'))

    val expectedAstForTest = Map(
      "t1" -> scan_t,
      "t2" -> Project(Schema("Name"), Schema("Name"), scan_t),
      "t3" -> Project(Schema("Name"), Schema("Name"),
                      Filter(Eq(Field("Flag"), Value("yes")),
                             scan_t)),
      "t4" -> Join(scan_t,
                   Project(Schema("Name1"), Schema("Name"), scan_t)),
      "t5" -> Join(scan_t,
                   Project(Schema("Name"), Schema("Name"), scan_t)),
      "t4h" -> HashJoin(scan_t,
                   Project(Schema("Name1"), Schema("Name"), scan_t)),
      "t5h" -> HashJoin(scan_t,
                   Project(Schema("Name"), Schema("Name"), scan_t)),
      "t6"  -> Group(Schema("Name"),Schema("Value"), scan_t),

      "t1gram1" -> scan_t1gram,
      "t1gram2" -> Filter(Eq(Field("Phrase"), Value("Auswanderung")), scan_t1gram)
    )
  }

  testquery("t1", "select * from t.csv")
  testquery("t2", "select Name from t.csv")
  testquery("t3", "select Name from t.csv where Flag='yes'")
  testquery("t4")
  testquery("t5")
  testquery("t4h")
  testquery("t5h")
  testquery("t6")

  testquery("t1gram1", "select * from t1gram.csv schema Phrase, Year, MatchCount, VolumeCount delim \\t")
  testquery("t1gram2", "select * from t1gram.csv schema Phrase, Year, MatchCount, VolumeCount delim \\t where Phrase='Auswanderung'")
}
