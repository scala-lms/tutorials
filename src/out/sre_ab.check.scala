/*****************************************
  Emitting Generated Code                  
*******************************************/
class Snippet extends ((java.lang.String)=>(Boolean)) {
def apply(x0:java.lang.String): Boolean = {
var x1: Int = 0
val x2 = x1
val x3 = x0.length
val x4 = x2 < x3
val x7 = if (x4) {
val x5 = x0(x2)
val x6 = 'a' == x5
x6
} else {
false
}
val x15 = if (x7) {
val x8 = x2 + 1
val x9 = x8 < x3
val x12 = if (x9) {
val x10 = x0(x8)
val x11 = 'b' == x10
x11
} else {
false
}
val x14 = x12
x14
} else {
false
}
var x16: Boolean = x15
val x40 = while ({val x17 = x16
val x22 = if (x17) {
false
} else {
val x19 = x1
val x20 = x19 < x3
x20
}
x22}) {
val x24 = x1 += 1
val x25 = x1
val x26 = x25 < x3
val x29 = if (x26) {
val x27 = x0(x25)
val x28 = 'a' == x27
x28
} else {
false
}
val x37 = if (x29) {
val x30 = x25 + 1
val x31 = x30 < x3
val x34 = if (x31) {
val x32 = x0(x30)
val x33 = 'b' == x32
x33
} else {
false
}
val x36 = x34
x36
} else {
false
}
x16 = x37
()
}
val x41 = x16
x41
}
}
/*****************************************
  End of Generated Code                  
*******************************************/
