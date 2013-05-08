/*****************************************
  Emitting Generated Code                  
*******************************************/
class Snippet extends ((java.lang.String)=>(Boolean)) {
def apply(x19:java.lang.String): Boolean = {
val x20 = x19.length
val x21 = x20 == 0
val x37 = if (x21) {
false
} else {
val x23 = x19(0)
val x24 = 'a' == x23
val x25 = false || x24
val x36 = if (x25) {
val x26 = x19.substring(1)
val x27 = x26.length
val x28 = x27 == 0
val x35 = if (x28) {
false
} else {
val x30 = x26(0)
val x31 = 'b' == x30
val x32 = false || x31
val x34 = if (x32) {
true
} else {
false
}
x34
}
x35
} else {
false
}
x36
}
x37
}
}
/*****************************************
  End of Generated Code                  
*******************************************/
