/*****************************************
  Emitting Generated Code                  
*******************************************/
class Snippet extends ((java.lang.String)=>(Boolean)) {
def apply(x55:java.lang.String): Boolean = {
var x97 = null.asInstanceOf[scala.Function1[Int, Boolean]]
val x56 = x55.length
val x57 = {x58: (Int) => 
var x81 = null.asInstanceOf[scala.Function1[Int, Boolean]]
val x59 = x55.substring(x58)
val x60 = x59.length
val x61 = {x62: (Int) => 
val x63 = x59.substring(x62)
val x64 = x63.length
val x65 = x64 == 0
val x72 = if (x65) {
false
} else {
val x67 = x63(0)
val x68 = 'b' == x67
val x69 = false || x68
val x71 = if (x69) {
true
} else {
false
}
x71
}
x72: Boolean
}
val x73 = {x74: (Int) => 
val x75 = x74 < x60
val x80 = if (x75) {
val x76 = x59(x74)
val x77 = 'a' == x76
val x78 = false || x77
val x79 = !x78
x79
} else {
false
}
x80: Boolean
}
x81 = {x82: (Int) => 
val x83 = x82 > x60
val x93 = if (x83) {
false
} else {
val x84 = x61(x82)
val x91 = if (x84) {
true
} else {
val x85 = x73(x82)
val x89 = if (x85) {
false
} else {
val x86 = x82 + 1
val x87 = x81(x86)
x87
}
x89
}
x91
}
x93: Boolean
}
val x95 = x81(0)
x95: Boolean
}
x97 = {x98: (Int) => 
val x99 = x98 > x56
val x106 = if (x99) {
false
} else {
val x100 = x57(x98)
val x104 = if (x100) {
true
} else {
val x101 = x98 + 1
val x102 = x97(x101)
x102
}
x104
}
x106: Boolean
}
val x108 = x97(0)
x108
}
}
/*****************************************
  End of Generated Code                  
*******************************************/
