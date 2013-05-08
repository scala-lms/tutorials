/*****************************************
  Emitting Generated Code                  
*******************************************/
class Snippet extends ((java.lang.String)=>(Boolean)) {
def apply(x36:java.lang.String): Boolean = {
var x59 = null.asInstanceOf[scala.Function1[Int, Boolean]]
val x37 = x36.length
val x38 = {x39: (Int) => 
val x40 = x36.substring(x39)
val x41 = x40.length
val x42 = x41 == 0
val x58 = if (x42) {
false
} else {
val x44 = x40(0)
val x45 = 'a' == x44
val x46 = false || x45
val x57 = if (x46) {
val x47 = x40.substring(1)
val x48 = x47.length
val x49 = x48 == 0
val x56 = if (x49) {
false
} else {
val x51 = x47(0)
val x52 = 'b' == x51
val x53 = false || x52
val x55 = if (x53) {
true
} else {
false
}
x55
}
x56
} else {
false
}
x57
}
x58: Boolean
}
x59 = {x60: (Int) => 
val x61 = x60 > x37
val x68 = if (x61) {
false
} else {
val x62 = x38(x60)
val x66 = if (x62) {
true
} else {
val x63 = x60 + 1
val x64 = x59(x63)
x64
}
x66
}
x68: Boolean
}
val x70 = x59(0)
x70
}
}
/*****************************************
  End of Generated Code                  
*******************************************/
