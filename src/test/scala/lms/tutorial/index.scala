/** 

Tutorials and Documentation
===========================

This tutorial series introduces _Lightweight Modular Staging_ (LMS), a
technique for embedding domain-specific languages (DSL) in Scala, using a
runtime code generation approach.

Outline:
<div id="tableofcontents"></div>


Self-Contained Tutorials
------------------------

The following tutorials are available:

- [Getting Started](start.html)
    Rep[T] vs T
    Rep[A => B] vs Rep[A]=>Rep[B]
    Rep[Range] vs Range


- [Shonan HMM Challenge](shonan.html)
    Sparse matrix vector multiplication
    Selective unrolling and precomputation

- [Regular Expressions](regex.html)
    From interpreters to compilers using staging

- [Ackermann's Function](ack.html)

- [Automata and Regexp Take 2](automata.html)
    Turning recursive functions into automata
    NFA to DFA conversion using staging

- [SQL Engine](csv.html)
    Efficient data processing

- [Fast Fourier Transform (FFT)](fft.html)

- [Sliding Stencil](stencil.html)


In-Depth Documentation
----------------------

Tentative restructuring

### Getting Started

- Why generative programming?
    _Because abstraction without regret!_
- Why LMS?
    _Because batteries included and hackable!_
- Hellow World


### Design Patterns

Purely generative:

- Specialization on static data
- Abstractions in the meta-language
- From interpreter to compiler
- Mixed-stage data structures
- Type classes and generic programming

Including IR:

- DSLs with multiple abstraction levels


### From Zero to LMS

- Program generation with Strings
- Program generation with Trees
- Program generation with Graphs


### LMS Internals

- Graph IR
- Effects
- Code Motion
- Transformers
- Code Generators

Advanced:

- Functions
- Structs


### Integration

- LMS for Scala
- LMS for JavaScript
- LMS for C

Advanced:

- LMS for Scala Macros
- LMS for Vector Intrinsics
- LMS for GPUs
- LMS for FPGAs
- LMS for SMT Solvers
- LMS for Truffle / Graal


In-Depth Documentation
----------------------

1. [Getting Started](01_overview.html)
    1. About Generative Programming
        1. Productivity vs Performance
        1. About Generative Programming
        1. Generative Programming Patterns
        1. Staging and Extensible Compilers
        1. Domain-Specific Languages
    3. LMS Basics
        1. Rep Types
        2. Staging as a Scala library
        3. Comparing LMS to other systems
    4. Installing LMS
    5. Getting Help
    6. Summary

1. [Generative programming and LMS basics](02_basics.html)
    1. Generative Programming Basics
        1. Program Generation with Strings
        1. Program Generation with Quasi-Quotes
        1. Syntactic Correctness through Deep Reuse of Syntax
        1. Scope Correctness through Deep Reuse of Scope
        1. Type Correctness through Deep Reuse of Types
        1. Value Correctness is an Open Problem
        1. Let Insertion as a Remedy
    1. The LMS Way
        1. Value Correctness through Deep Reuse of Evaluation Order
        1. Removing Syntactic Overhead
        1. Staging as a Library and Modular Definition of Object Languages
            1. Syntax correctness through Embedding as Methods
            1. Scope Correctness through Deep Reuse Of Val Bindings
            1. Type Correctness through Typed Embedding (Deep Reuse of Types)
            1. Value Correctness through Deep Reuse of Evaluation Order
        1. Functions and Recursion 
        1. Generating and Loading Executable Code

1. [Intermediate representation and optimzations](03_compiler.html)
    1. Intro: Not your Grandfather's Compiler
    1. Intermediate Representation: Trees
        1. Trees Instead of Strings
            1. Modularity: Adding IR Node Types
        1. Enabling Analysis and Transformation
            1. Modularity: Adding Traversal Passes
            1. Solving the ''Expression Problem''
            1. Generating Code
            1. Modularity: Adding Transformations
            1. Transformation by Iterated Staging
        1. Problem: Phase Ordering
    1. Intermediate Representation: Graphs
        1. Purely Functional Subset
        1. Modularity: Adding IR Node Types
        1. Simpler Analysis and More Flexible Transformations
            1. Common Subexpression Elimination/Global Value Numbering
            1. Pattern Rewrites
            1. Modularity: Adding new Optimizations
            1. Context- and Flow-Sensitive Transformations
            1. Graph Transformations
            1. Dead Code Elimination
        1. From Graphs Back to Trees
            1. Code Motion
            1. Tree-Like Traversals and Transformers
        1. Effects
            1. Simple Effect Domain
            1. Fine Grained Effects: Tracking Mutations per Allocation Site
    1. Advanced Optimizations
        1. Rewriting
            1. Context-Sensitive Rewriting
            1. Speculative Rewriting: Combining Analyses and Transformations
            1. Delayed Rewriting and Multi-Level IR
        1. Splitting and Combining Statements
            1. Effectful Statements
            1. Data Structures
            1. Representation Conversion
        1. Loop Fusion and Deforestation


1. [Staging and LMS at work](04_atwork.html)
    1. Intro: Abstraction Without Regret
    1. Common Compiler Optimizations
    1. Delite: An End-to-End System for Embedded Parallel DSLs
        1. Building a Simple DSL
        1. Code Generation
        1. The Delite Compiler Framework and Runtime
    1. Control Abstraction
        1. Leveraging Higher-Order Functions in the Generator
        1. Using Continuations in the Generator to Implement Backtracking
        1. Using Continuations in the Generator to Generate Async Code Patterns
        1. Guarantees by Construction
    1. Data Abstraction
        1. Static Data Structures
        1. Dynamic Data Structures with Partial Evaluation
        1. Generic Programming with Type Classes
        1. Unions and Inheritance
        1. Struct of Array and Other Data Format Conversions
        1. Loop Fusion and Deforestation
        1. Extending the Framework
        1. Lowering Transforms}
    1. Case Studies
        1. OptiML Stream Example
        1. OptiQL Struct Of Arrays Example


*/