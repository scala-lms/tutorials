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
val x41 = if (x5) {
var x6: Int = 1
val x7 = x6
var x8: Boolean = true
var x9: Boolean = false
val x34 = while ({val x10 = x9
val x15 = if (x10) {
false
} else {
val x12 = x8
val x13 = !x12
x13
}
val x19 = if (x15) {
val x16 = x6
val x17 = x16 < x1
x17
} else {
false
}
x19}) {
val x21 = x9
val x28 = if (x21) {
false
} else {
val x23 = x6
val x24 = x0(x23)
val x25 = 'b' == x24
val x26 = !x25
x26
}
x9 = x28
val x30 = x6 += 1
val x31 = x6
x8 = true
()
}
val x35 = x9
val x39 = if (x35) {
false
} else {
val x37 = x8
x37
}
x39
} else {
false
}
x41
}
}
/*****************************************
  End of Generated Code                  
*******************************************/
