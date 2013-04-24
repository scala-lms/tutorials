name := "scala.lms.tutorials"

organization := "ch.epfl"

scalaOrganization := "org.scala-lang.virtualized"

scalaVersion := "2.10.1"

libraryDependencies += "ch.epfl" %% "scala.lms" % "0.4-SNAPSHOT"

scalacOptions += "-Yvirtualize"