[![Build Status](https://api.travis-ci.org/scala-lms/tutorials.png)](https://travis-ci.org/scala-lms/tutorials)

[Tutorials](http://scala-lms.github.io/tutorials/) on [LMS](http://scala-lms.github.io)
======================================================================

Lightweight Modular Staging (LMS) is a framework for runtime code generation and compiled DSLs.

Dependencies
------------

* [SBT](http://www.scala-sbt.org/):
  * Create or edit the file `~/.sbtconfig` to contain the following options for the JVM:

    `SBT_OPTS="-Xms3G -Xmx3G -Xss1M -XX:MaxPermSize=192M -XX:+UseParallelGC"`

* [LMS](https://github.com/TiarkRompf/virtualization-lms-core):
  * `git clone https://github.com/TiarkRompf/virtualization-lms-core.git`
  * `sbt publish-local`

SBT commands
------------

* `test` -- runs all the tests

Browse the sources
------------------

* The [src/test/scala/lms/tutorial](src/test/scala/lms/tutorial) directory contains the source files from which the [tutorial website](http://scala-lms.github.io/tutorials) is generated. The website generation is handled in [the main website repository](https://github.com/scala-lms/scala-lms.github.com#maintainers).
