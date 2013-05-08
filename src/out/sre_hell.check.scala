/*****************************************
  Emitting Generated Code                  
*******************************************/
class Snippet extends ((java.lang.String)=>(Boolean)) {
def apply(x54:java.lang.String): Boolean = {
var x95 = null.asInstanceOf[scala.Function1[Int, Boolean]]
val x55 = x54.length
val x56 = {x57: (Int) => 
val x58 = x54.substring(x57)
val x59 = x58.length
val x60 = x59 == 0
val x94 = if (x60) {
false
} else {
val x62 = x58(0)
val x63 = 'h' == x62
val x64 = false || x63
val x93 = if (x64) {
val x65 = x58.substring(1)
val x66 = x65.length
val x67 = x66 == 0
val x92 = if (x67) {
false
} else {
val x69 = x65(0)
val x70 = 'e' == x69
val x71 = false || x70
val x91 = if (x71) {
val x72 = x65.substring(1)
val x73 = x72.length
val x74 = x73 == 0
val x90 = if (x74) {
false
} else {
val x76 = x72(0)
val x77 = 'l' == x76
val x78 = false || x77
val x89 = if (x78) {
val x79 = x72.substring(1)
val x80 = x79.length
val x81 = x80 == 0
val x88 = if (x81) {
false
} else {
val x83 = x79(0)
val x84 = 'l' == x83
val x85 = false || x84
val x87 = if (x85) {
true
} else {
false
}
x87
}
x88
} else {
false
}
x89
}
x90
} else {
false
}
x91
}
x92
} else {
false
}
x93
}
x94: Boolean
}
x95 = {x96: (Int) => 
val x97 = x96 > x55
val x104 = if (x97) {
false
} else {
val x98 = x56(x96)
val x102 = if (x98) {
true
} else {
val x99 = x96 + 1
val x100 = x95(x99)
x100
}
x102
}
x104: Boolean
}
val x106 = x95(0)
x106
}
}
/*****************************************
  End of Generated Code                  
*******************************************/
