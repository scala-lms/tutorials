name := "lms-tutorials"

//scalaOrganization := "org.scala-lang.virtualized"

scalaVersion := "2.11.2"

libraryDependencies += "EPFL" %% "macro-lms" % "1.0.0-macrovirt"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.2"


//libraryDependencies += "org.scala-lang.virtualized" % "scala-compiler" % "2.11.2"

//libraryDependencies += "org.scala-lang.virtualized" % "scala-library" % "2.11.2"

//libraryDependencies += "org.scala-lang.virtualized" % "scala-reflect" % "2.11.2"

//libraryDependencies += "org.scala-lang.virtualized" % "scala-actors" % "2.11.2" % "test" //TODO: speacial virtualization?

//scalacOptions += "-Yvirtualize"

//scalacOptions += "-deprecation"


//macro trans:

libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value % "compile"

libraryDependencies += "org.scala-lang" % "scala-library" % scalaVersion.value % "compile"

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value % "compile"

libraryDependencies += "org.scala-lang.virtualized" %% "scala-virtualized" % "1.0.0-macrovirt"

autoCompilerPlugins := true

val paradiseVersion = "2.0.1"

libraryDependencies ++= (
  if (scalaVersion.value.startsWith("2.10")) List("org.scalamacros" %% "quasiquotes" % paradiseVersion)
  else Nil
  )

addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)
