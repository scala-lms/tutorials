/*****************************************
  Emitting Generated Code                  
*******************************************/
class Snippet extends ((java.lang.String)=>(Boolean)) {
def apply(x0:java.lang.String): Boolean = {
val x1 = x0.length
val x2 = 0 < x1
val x5 = if (x2) {
val x3 = x0(0)
val x4 = 'a' == x3
x4
} else {
false
}
val x39 = if (x5) {
var x6: Int = 1
val x7 = x6
val x8 = x7 == x1
var x9: Boolean = x8
var x10: Boolean = false
val x32 = while ({val x11 = x10
val x16 = if (x11) {
false
} else {
val x13 = x9
val x14 = !x13
x14
}
val x20 = if (x16) {
val x17 = x6
val x18 = x17 < x1
x18
} else {
false
}
x20}) {
val x22 = x6
val x23 = x0(x22)
val x24 = 'b' == x23
val x25 = !x24
x10 = x25
val x27 = x6 += 1
val x28 = x6
val x29 = x28 == x1
x9 = x29
()
}
val x33 = x10
val x37 = if (x33) {
false
} else {
val x35 = x9
x35
}
x37
} else {
false
}
x39
}
}
/*****************************************
  End of Generated Code                  
*******************************************/
