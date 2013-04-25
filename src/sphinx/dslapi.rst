.. contents::

DSL API
=======

We introduce the concepts common to all DSLs built with LMS.

Concepts
--------

Staging
```````

In LMS, a DSL program is split into two stages: the first stage
generates the second stage. The DSL program is partially evaluated
during the first stage -- whether an expression belongs to the first
or second stage is driven by types: expressions of type ``Rep[T]`` may
be deferred to the second stage, while other expressions are evaluated
during the first stage, becoming constants in the second stage.

.. container:: side-by-side

   .. container:: left

      .. includecode:: ../test/scala/lms/tutorial/dslapi.scala
         :include: 1
      .. includecode:: ../out/dslapi1.check.scala

   .. container:: right

      .. includecode:: ../test/scala/lms/tutorial/dslapi.scala
         :include: 2
      .. includecode:: ../out/dslapi2.check.scala

Loops can be unrolled in the first stage, or be generated as loops in
the second stage, driven by the type of their condition.

.. container:: side-by-side

   .. container:: left

      .. includecode:: ../test/scala/lms/tutorial/dslapi.scala
         :include: range1
      .. includecode:: ../out/dslapirange1.check.scala
         :include: for

   .. container:: right

      .. includecode:: ../test/scala/lms/tutorial/dslapi.scala
         :include: range2
      .. includecode:: ../out/dslapirange2.check.scala
         :include: for

With this fine-grain control, conditional loop unrolling can be
implemented at the DSL user level.

.. includecode:: ../test/scala/lms/tutorial/dslapi.scala
   :include: unrollIf

We can use this conditional loop unrolling to optimize multiplying a
matrix known at staging time with a vector. For example, consider a
matrix with one dense rows, and otherwise sparse rows:

.. includecode:: ../test/scala/lms/tutorial/dslapi.scala
   :include: ex-a

When scanning the columns, we would like to generate a loop for the
dense row, and unroll the loop for the sparse rows.

.. includecode:: ../test/scala/lms/tutorial/dslapi.scala
   :include: matrix_vector_prod

Let's compare the code generated for a dense vs sparse row.

.. container:: side-by-side

   .. container:: left

      .. includecode:: ../out/dslapishonan-hmm.check.scala
         :include: index_0

   .. container:: right

      .. includecode:: ../out/dslapishonan-hmm.check.scala
         :include: index_2
