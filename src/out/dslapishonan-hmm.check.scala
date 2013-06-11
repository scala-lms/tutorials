/*****************************************
  Emitting Generated Code                  
*******************************************/
class Snippet(px5:Array[Int]) extends ((Array[Int])=>(Array[Int])) {
def apply(x0:Array[Int]): Array[Int] = {
val x1 = new Array[Int](5)
val x5 = px5 // static data: Array(1,1,1,1,1)
val x14 = {
//#row_0
// generated code for row 0
var x3 : Int = 0
val x12 = while (x3 < 5) {
val x4 = x1(0)
val x6 = x5(x3)
val x7 = x0(x3)
val x8 = x6 * x7
val x9 = x4 + x8
val x10 = x1(0) = x9
x3 = x3 + 1
}
x12
//#row_0
}
val x24 = {
//#row_1
// generated code for row 1
val x15 = x1(1)
val x18 = x1(1) = x15
()
//#row_1
}
val x20 = x0(2)
val x31 = {
//#row_2
// generated code for row 2
val x25 = x1(2)
val x27 = x1(2) = x25
val x28 = x25 + x20
val x29 = x1(2) = x28
()
//#row_2
}
val x36 = {
//#row_3
// generated code for row 3
val x32 = x1(3)
val x34 = x1(3) = x32
()
//#row_3
}
val x22 = x0(4)
val x45 = {
//#row_4
// generated code for row 4
val x37 = x1(4)
val x39 = x1(4) = x37
val x40 = x37 + x20
val x41 = x1(4) = x40
val x42 = x40 + x22
val x43 = x1(4) = x42
()
//#row_4
}
x1
}
}
/*****************************************
  End of Generated Code                  
*******************************************/
