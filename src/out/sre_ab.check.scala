/*****************************************
  Emitting Generated Code                  
*******************************************/
class Snippet extends ((java.lang.String)=>(Boolean)) {
def apply(x0:java.lang.String): Boolean = {
var x1: Int = 0
val x2 = x1
val x3 = x0.length
val x4 = x2 < x3
val x8 = if (x4) {
val x5 = x0(x2)
val x6 = 'a' == x5
val x7 = false || x6
x7
} else {
false
}
val x17 = if (x8) {
val x9 = x2 + 1
val x10 = x9 < x3
val x14 = if (x10) {
val x11 = x0(x9)
val x12 = 'b' == x11
val x13 = false || x12
x13
} else {
false
}
val x16 = if (x14) {
true
} else {
false
}
x16
} else {
false
}
var x18: Boolean = x17
val x44 = while ({val x19 = x18
val x24 = if (x19) {
false
} else {
val x21 = x1
val x22 = x21 < x3
x22
}
x24}) {
val x26 = x1 += 1
val x27 = x1
val x28 = x27 < x3
val x32 = if (x28) {
val x29 = x0(x27)
val x30 = 'a' == x29
val x31 = false || x30
x31
} else {
false
}
val x41 = if (x32) {
val x33 = x27 + 1
val x34 = x33 < x3
val x38 = if (x34) {
val x35 = x0(x33)
val x36 = 'b' == x35
val x37 = false || x36
x37
} else {
false
}
val x40 = if (x38) {
true
} else {
false
}
x40
} else {
false
}
x18 = x41
()
}
val x45 = x18
x45
}
}
/*****************************************
  End of Generated Code                  
*******************************************/
