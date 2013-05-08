/*****************************************
  Emitting Generated Code                  
*******************************************/
class Snippet extends ((java.lang.String)=>(Boolean)) {
def apply(x70:java.lang.String): Boolean = {
var x127 = null.asInstanceOf[scala.Function1[Int, Boolean]]
val x71 = x70.length
val x72 = {x73: (Int) => 
val x74 = x70.substring(x73)
val x75 = x74.length
val x76 = x75 == 0
val x125 = if (x76) {
false
} else {
val x78 = x74(0)
val x79 = 'h' == x78
val x80 = false || x79
val x123 = if (x80) {
val x81 = x74.substring(1)
val x82 = x81.length
val x83 = x82 == 0
val x121 = if (x83) {
false
} else {
val x85 = x81(0)
val x86 = 'e' == x85
val x87 = false || x86
val x119 = if (x87) {
var x103 = null.asInstanceOf[scala.Function1[Int, Boolean]]
val x88 = x81.substring(1)
val x89 = x88.length
val x90 = {x91: (Int) => 
val x92 = x88.substring(x91)
val x93 = x92.length
val x94 = x93 == 0
x94: Boolean
}
val x95 = {x96: (Int) => 
val x97 = x96 < x89
val x102 = if (x97) {
val x98 = x88(x96)
val x99 = 'l' == x98
val x100 = false || x99
val x101 = !x100
x101
} else {
false
}
x102: Boolean
}
x103 = {x104: (Int) => 
val x105 = x104 > x89
val x115 = if (x105) {
false
} else {
val x106 = x90(x104)
val x113 = if (x106) {
true
} else {
val x107 = x95(x104)
val x111 = if (x107) {
false
} else {
val x108 = x104 + 1
val x109 = x103(x108)
x109
}
x111
}
x113
}
x115: Boolean
}
val x117 = x103(0)
x117
} else {
false
}
x119
}
x121
} else {
false
}
x123
}
x125: Boolean
}
x127 = {x128: (Int) => 
val x129 = x128 > x71
val x136 = if (x129) {
false
} else {
val x130 = x72(x128)
val x134 = if (x130) {
true
} else {
val x131 = x128 + 1
val x132 = x127(x131)
x132
}
x134
}
x136: Boolean
}
val x138 = x127(0)
x138
}
}
/*****************************************
  End of Generated Code                  
*******************************************/
