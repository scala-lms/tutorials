name := "lms-tutorials"

scalaVersion := "2.12.8"

resolvers += Resolver.sonatypeRepo("snapshots")

// libraryDependencies += "org.scala-lang.lms" %% "lms-core-macrovirt" % "0.9.0-SNAPSHOT"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.4"

libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value % "compile"

libraryDependencies += "org.scala-lang" % "scala-library" % scalaVersion.value % "compile"

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value % "compile"

autoCompilerPlugins := true

val paradiseVersion = "2.1.0"

addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)

// tests are not thread safe
parallelExecution in Test := false

lazy val tutorials = (project in file(".")).dependsOn(lms % "test->test")
  // .settings(fork := true)

lazy val lms = ProjectRef(file("../lms-clean"), "lms-clean")
  // .settings(fork := true)
