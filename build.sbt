name := "lms-tutorials"

scalaOrganization := "org.scala-lang.virtualized"

scalaVersion := "2.11.2"

libraryDependencies += "org.scala-lang.virtualized" % "scala-compiler" % "2.11.2"

libraryDependencies += "org.scala-lang.virtualized" % "scala-library" % "2.11.2"

libraryDependencies += "org.scala-lang.virtualized" % "scala-reflect" % "2.11.2"

libraryDependencies += "EPFL" %% "lms" % "0.3-SNAPSHOT"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.2"

libraryDependencies += "org.scala-lang.virtualized" % "scala-actors" % "2.11.2" % "test"

scalacOptions += "-Yvirtualize"

scalacOptions += "-deprecation"
