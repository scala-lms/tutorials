.. contents::

DSL API
=======

We introduce the concepts common to all DSLs built with LMS.

Staging
-------

In LMS, a DSL program is split into two stages: the first stage
generates the second stage. The DSL program is partially evaluated
during the first stage -- whether an expression belongs to the first
or second stage is driven by types: expressions of type ``Rep[T]`` may
be deferred to the second stage, while other expressions are evaluated
during the first stage and become constants in the second stage.

.. includecode:: ../test/scala/lms/tutorial/dslapi.scala
   :include: 1
.. includecode:: ../out/dslapi1.check.scala

.. includecode:: ../test/scala/lms/tutorial/dslapi.scala 
   :include: 2
.. includecode:: ../out/dslapi2.check.scala              

