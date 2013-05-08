/*****************************************
  Emitting Generated Code                  
*******************************************/
class Snippet extends ((java.lang.String)=>(Boolean)) {
def apply(x68:java.lang.String): Boolean = {
var x123 = null.asInstanceOf[scala.Function1[Int, Boolean]]
val x69 = x68.length
val x88 = {x89: (Int) => 
true: Boolean
}
val x70 = {x71: (Int) => 
val x72 = x68.substring(x71)
val x73 = x72.length
val x74 = x73 == 0
val x121 = if (x74) {
false
} else {
val x76 = x72(0)
val x77 = 'h' == x76
val x78 = false || x77
val x119 = if (x78) {
val x79 = x72.substring(1)
val x80 = x79.length
val x81 = x80 == 0
val x117 = if (x81) {
false
} else {
val x83 = x79(0)
val x84 = 'e' == x83
val x85 = false || x84
val x115 = if (x85) {
var x99 = null.asInstanceOf[scala.Function1[Int, Boolean]]
val x86 = x79.substring(1)
val x87 = x86.length
val x91 = {x92: (Int) => 
val x93 = x92 < x87
val x98 = if (x93) {
val x94 = x86(x92)
val x95 = 'l' == x94
val x96 = false || x95
val x97 = !x96
x97
} else {
false
}
x98: Boolean
}
x99 = {x100: (Int) => 
val x101 = x100 > x87
val x111 = if (x101) {
false
} else {
val x102 = x88(x100)
val x109 = if (x102) {
true
} else {
val x103 = x91(x100)
val x107 = if (x103) {
false
} else {
val x104 = x100 + 1
val x105 = x99(x104)
x105
}
x107
}
x109
}
x111: Boolean
}
val x113 = x99(0)
x113
} else {
false
}
x115
}
x117
} else {
false
}
x119
}
x121: Boolean
}
x123 = {x124: (Int) => 
val x125 = x124 > x69
val x132 = if (x125) {
false
} else {
val x126 = x70(x124)
val x130 = if (x126) {
true
} else {
val x127 = x124 + 1
val x128 = x123(x127)
x128
}
x130
}
x132: Boolean
}
val x134 = x123(0)
x134
}
}
/*****************************************
  End of Generated Code                  
*******************************************/
