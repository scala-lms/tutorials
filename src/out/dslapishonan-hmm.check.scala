/*****************************************
  Emitting Generated Code                  
*******************************************/
class Snippet(px5:Array[Int]) extends ((Array[Int])=>(Array[Int])) {
def apply(x0:Array[Int]): Array[Int] = {
val x1 = new Array[Int](5)
val x5 = px5 // static data: Array(1,1,1,1,1)
val x14 = {
//#index_0
// generated code
var x3 : Int = 0
val x12 = while (x3 < 5) {
val x4 = x1(0)
val x6 = x5(x3)
val x7 = x0(x3)
val x8 = x6 * x7
val x9 = x4 + x8
val x10 = x1(0) = x9
x10
x3 = x3 + 1
}
x12
//#index_0
}
val x24 = {
//#index_1
// generated code
val x15 = x1(1)
val x18 = x1(1) = x15
()
//#index_1
}
val x20 = x0(2)
val x31 = {
//#index_2
// generated code
val x25 = x1(2)
val x27 = x1(2) = x25
val x28 = x25 + x20
val x29 = x1(2) = x28
()
//#index_2
}
val x36 = {
//#index_3
// generated code
val x32 = x1(3)
val x34 = x1(3) = x32
()
//#index_3
}
val x22 = x0(4)
val x45 = {
//#index_4
// generated code
val x37 = x1(4)
val x39 = x1(4) = x37
val x40 = x37 + x20
val x41 = x1(4) = x40
val x42 = x40 + x22
val x43 = x1(4) = x42
()
//#index_4
}
x1
}
}
/*****************************************
  End of Generated Code                  
*******************************************/
