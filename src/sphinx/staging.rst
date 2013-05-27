.. contents::

Staging
=======

What is staging? The idea behind staging is to delay computation of
certain expressions, generating code to compute them later. The
benefit is *abstraction without regret*.

Lightweight Modular Staging (LMS)
---------------------------------

Lighweight Modular Staging (LMS) is a staging technique driven by
types. The technique has been implemented in Scala, in a framework to
build compilers.

In addition to the *staging* aspect, the technique is *lightweight*,
because it is purely library based. It is also *modular*, for two
reasons: (1) features can be mixed and matched (2) DSL specification
and implementation are separate.

``Rep[T]`` vs ``T``
```````````````````

In LMS, ``Rep[T]`` represents a delayed compuation of type `T`. Thus,
during staging, an expression of type ``Rep[T]`` becomes part of
the generated code, while an expression of bare type ``T`` becomes a
constant in the generated code. For core Scala features, adding
``Rep`` types should be enough to build a program generator, as we
will see later.

Contrast the snippet on the left, where ``b`` is a ``Boolean``, with
the snippet on the right, where ``b`` is a ``Rep[Boolean]``. The
expression ``if (b) 1 else x`` is executed at staging time when ``b``
is a ``Boolean``, while it is delayed causing code to be generated for
the ``if`` expression when ``b`` is a ``Rep[Boolean]`` -- indeed, the
actual value of ``b`` is not known at staging time, but only when the
generated code is executed.

.. container:: side-by-side

   .. container:: left

      .. includecode:: ../test/scala/lms/tutorial/dslapi.scala
         :include: 1
      .. includecode:: ../out/dslapi1.check.scala

   .. container:: right

      .. includecode:: ../test/scala/lms/tutorial/dslapi.scala
         :include: 2
      .. includecode:: ../out/dslapi2.check.scala

``Rep[A => B]`` vs ``Rep[A]=>Rep[B]``
```````````````````````````````````````

In the previous snippets, we already notice some *abstraction without
regret*: the ``compute`` function exists only at staging time, and is
not part of the generated code -- more generally, we can freely use
abstractions to structure and compose the staged program, but these
abstractions are not part of the generated code when their type is a
bare ``T`` as opposed of a ``Rep[T]``. In the right snippet,
``compute`` has the type ``Rep[Boolean] => Rep[Int]``, not
``Rep[Boolean => Int]`` -- its type already tells us that the function
is known at staging time.

``Rep[Range] vs Range``
```````````````````````
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

Shonan Challenge
````````````````

With this fine-grain control, conditional loop unrolling can be
implemented at the DSL user level.

.. includecode:: ../test/scala/lms/tutorial/dslapi.scala
   :include: unrollIf

We can use this conditional loop unrolling to optimize multiplying a
matrix known at staging time with a vector. For example, consider a
matrix with a mix of dense and sparse rows:

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
         :include: row_0

   .. container:: right

      .. includecode:: ../out/dslapishonan-hmm.check.scala
         :include: row_2
