name := "lms-tutorials"

scalaOrganization := "org.scala-lang.virtualized"

scalaVersion := "2.10.1-V1"

autoScalaLibrary := false

libraryDependencies += "org.scala-lang.virtualized" % "scala-compiler" % "2.10.1-V1"

libraryDependencies += "org.scala-lang.virtualized" % "scala-library" % "2.10.1-V1"

libraryDependencies += "org.scala-lang.virtualized" % "scala-reflect" % "2.10.1-V1"

libraryDependencies += "EPFL" %% "lms" % "0.3-SNAPSHOT" intransitive()

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test" intransitive()

libraryDependencies += "org.scala-lang.virtualized" % "scala-actors" % "2.10.1-V1" % "test"

scalacOptions += "-Yvirtualize"

scalacOptions += "-deprecation"

site.settings

site.sphinxSupport()

ghpages.settings

git.remoteRepo := "git@github.com:scala-lms/tutorials.git"