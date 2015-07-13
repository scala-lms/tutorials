[![Build Status](https://api.travis-ci.org/scala-lms/tutorials.png)](https://travis-ci.org/scala-lms/tutorials)

[Tutorials](http://scala-lms.github.io/tutorials/) on [LMS](http://scala-lms.github.io)
======================================================================

Lightweight Modular Staging (LMS) is a framework for runtime code generation and compiled DSLs.

How to build and run
--------------------

1. Install the [SBT](http://www.scala-sbt.org/) build tool:
    
    If you are using OS X and [Homebrew](http://brew.sh), run `brew install sbt`.

    For other platforms, follow the instructions on the [SBT](http://www.scala-sbt.org/) website.

2. Install [LMS](https://github.com/TiarkRompf/virtualization-lms-core):
  * `git clone https://github.com/TiarkRompf/virtualization-lms-core.git lms-core`
  * `cd lms-core; sbt publish-local; cd ..`

3. Clone this repo and run the tests:
  * `git clone https://github.com/scala-lms/tutorials.git lms-tutorials`
  * `cd lms-tutorials`
  * `sbt test`


Browse the sources
------------------

* The [src/test/scala/lms/tutorial](src/test/scala/lms/tutorial) directory contains the source files from which the [tutorial website](http://scala-lms.github.io/tutorials) is generated. The website generation is handled in [the main website repository](https://github.com/scala-lms/scala-lms.github.com#maintainers).
