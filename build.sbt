name := "lms-tutorials"

scalaVersion := "2.11.8"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies += "org.scala-lang.lms" %% "lms-core-macrovirt" % "0.9.0-SNAPSHOT"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.2"

libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value % "compile"

libraryDependencies += "org.scala-lang" % "scala-library" % scalaVersion.value % "compile"

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value % "compile"

autoCompilerPlugins := true

val paradiseVersion = "2.1.0"

libraryDependencies ++= (
  if (scalaVersion.value.startsWith("2.10")) List("org.scalamacros" %% "quasiquotes" % paradiseVersion)
  else Nil
  )

addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)
