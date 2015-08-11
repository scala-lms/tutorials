import scala.lms.tutorial.eval._
/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((Value)=>(Value)) {
  def apply(x0:Value): Value = {
    val x3 = {x4: (Value) =>
      x4: Value
    }
    val x1 = {x2: (Value) =>
      val x5 = base_apply_norep(x2, List(x2), Map(("fun" -> x0), ("F" -> x2)), x3)
      x5: Value
    }
    val x7 = evalfun(x1)
    val x8 = {x9: (Value) =>
      val x10 = {x11: (Value) =>
        val x12 = {x13: (Value) =>
          val x14 = base_apply_norep(x13, List(x11), Map(("fun" -> x0), ("F" -> x9), ("x" -> x11)), x3)
          x14: Value
        }
        val x16 = base_apply_norep(x9, List(x9), Map(("fun" -> x0), ("F" -> x9), ("x" -> x11)), x12)
        x16: Value
      }
      val x18 = evalfun(x10)
      val x19 = base_apply_norep(x0, List(x18), Map(("fun" -> x0), ("F" -> x9)), x3)
      x19: Value
    }
    val x21 = evalfun(x8)
    val x22 = {x23: (Value) =>
      x23: Value
    }
    val x24 = base_apply_norep(x7, List(x21), Map(("fun" -> x0)), x22)
    x24
  }
}
/*****************************************
End of Generated Code
*******************************************/
// compilation: ok
import scala.lms.tutorial.eval._
/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((Value)=>(Value)) {
  def apply(x0:Value): Value = {
    val x14 = {x15: (Value) =>
      x15: Value
    }
    val x1 = {x2: (Value) =>
      val x8 = {x9: (Value) =>
        val x12 = {x13: (Value) =>
          val x16 = base_apply_norep(P("+"), List(x9, x13), Map(("fib" -> x0), ("n" -> x2)), x14)
          x16: Value
        }
        val x10 = {x11: (Value) =>
          val x18 = base_apply_norep(x0, List(x11), Map(("fib" -> x0), ("n" -> x2)), x12)
          x18: Value
        }
        val x20 = base_apply_norep(P("-"), List(x2, I(2)), Map(("fib" -> x0), ("n" -> x2)), x10)
        x20: Value
      }
      val x6 = {x7: (Value) =>
        val x22 = base_apply_norep(x0, List(x7), Map(("fib" -> x0), ("n" -> x2)), x8)
        x22: Value
      }
      val x3 = {x4: (Value) =>
        val x5 = B(false) != x4
        val x26 = if (x5) {
          x2
        } else {
          val x24 = base_apply_norep(P("-"), List(x2, I(1)), Map(("fib" -> x0), ("n" -> x2)), x6)
          x24
        }
        x26: Value
      }
      val x28 = base_apply_norep(P("<"), List(x2, I(2)), Map(("fib" -> x0), ("n" -> x2)), x3)
      x28: Value
    }
    val x30 = evalfun(x1)
    x30
  }
}
/*****************************************
End of Generated Code
*******************************************/
// compilation: ok
