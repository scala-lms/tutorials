[![Build Status](https://api.travis-ci.org/scala-lms/tutorials)](https://travis-ci.org/scala-lms/tutorials)

[Tutorials](http://scala-lms.github.io/tutorials/) on [LMS](http://scala-lms.github.io)
======================================================================

Lightweight Modular Staging (LMS) is a framework for runtime code generation and compiled DSLs.

Dependencies
------------

* [SBT](http://www.scala-sbt.org/)
  * Create or edit the file `~/.sbtconfig` to contain the following options for the JVM:

    `SBT_OPTS="-Xms3G -Xmx3G -Xss1M -XX:MaxPermSize=192M -XX:+UseParallelGC"`

* [LMS](https://github.com/TiarkRompf/virtualization-lms-core):
  * `git clone https://github.com/TiarkRompf/virtualization-lms-core.git`
  * `sbt publish-local`

SBT commands for learners
-------------------------

* `test` -- runs all the tests

SBT commands for maintainers
-----------------------------
* `preview-site` -- previews the website locally
* `ghpages-push-site` -- updates the [live website](http://scala-lms.github.io/tutorials)
