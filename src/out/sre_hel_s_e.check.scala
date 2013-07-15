/*****************************************
  Emitting Generated Code                  
*******************************************/
class Snippet extends ((java.lang.String)=>(Boolean)) {
def apply(x0:java.lang.String): Boolean = {
var x1: Int = -1
var x2: Boolean = false
val x6 = x0.length
val x64 = while ({val x3 = x2
val x9 = if (x3) {
false
} else {
val x5 = x1
val x7 = x5 < x6
x7
}
x9}) {
val x11 = x1 += 1
val x12 = x1
val x13 = x12 < x6
val x16 = if (x13) {
val x14 = x0(x12)
val x15 = 'h' == x14
x15
} else {
false
}
val x61 = if (x16) {
val x17 = x12 + 1
val x18 = x17 < x6
val x21 = if (x18) {
val x19 = x0(x17)
val x20 = 'e' == x19
x20
} else {
false
}
val x60 = if (x21) {
val x22 = x17 + 1
var x23: Int = x22
val x24 = x23
val x25 = x24 == x6
var x26: Boolean = x25
var x27: Boolean = false
val x53 = while ({val x28 = x27
val x33 = if (x28) {
false
} else {
val x30 = x26
val x31 = !x30
x31
}
val x37 = if (x33) {
val x34 = x23
val x35 = x34 < x6
x35
} else {
false
}
x37}) {
val x39 = x27
val x46 = if (x39) {
false
} else {
val x41 = x23
val x42 = x0(x41)
val x43 = 'l' == x42
val x44 = !x43
x44
}
x27 = x46
val x48 = x23 += 1
val x49 = x23
val x50 = x49 == x6
x26 = x50
()
}
val x54 = x27
val x58 = if (x54) {
false
} else {
val x56 = x26
x56
}
x58
} else {
false
}
x60
} else {
false
}
x2 = x61
()
}
val x65 = x2
x65
}
}
/*****************************************
  End of Generated Code                  
*******************************************/
