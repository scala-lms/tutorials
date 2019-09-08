/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((Array[Double])=>(Array[Double])) {
  def apply(x0:Array[Double]): Array[Double] = {
    val x1 = x0.length
    val x2 = new Array[Double](x1)
    val x3 = x1 - 2
    val x57 = x3 > 2
    val x114 = if (x57) {
      val x58 = x0(2)
      val x59 = x0(3)
      val x60 = x58 * x59
      val x61 = x58 - x60
      val x62 = x0(1)
      val x63 = x62 * x58
      val x64 = x61 + x63
      val x65 = x0(4)
      val x66 = x59 * x65
      val x67 = x59 - x66
      val x68 = x67 + x60
      val x69 = x64 * x68
      val x70 = x64 - x69
      val x71 = x62 - x63
      val x72 = x0(0)
      val x73 = x72 * x62
      val x74 = x71 + x73
      val x75 = x74 * x64
      val x76 = x70 + x75
      val x77 = x2(2) = x76
      var x78: Double = x65
      var x79: Double = x66
      var x80: Double = x68
      var x81: Double = x69
      var x82: Int = 3
      var x83: Int = 4
      var x85 : Int = 3
      val x112 = while (x85 < x3) {
        // variable reads
        val x87 = x78
        val x88 = x79
        val x89 = x80
        val x90 = x81
        val x91 = x82
        val x92 = x83
        // computation
        val x95 = x85 + 2
        val x96 = x0(x95)
        val x97 = x87 * x96
        val x98 = x87 - x97
        val x99 = x98 + x88
        val x100 = x89 * x99
        val x101 = x89 - x100
        val x102 = x101 + x90
        val x103 = x2(x91) = x102
        // variable writes
        x78 = x96
        x79 = x97
        x80 = x99
        x81 = x100
        x82 = x92
        x83 = x95
        x85 = x85 + 1
      }
      x112
    } else {
      ()
    }
    x2
  }
}
/*****************************************
End of Generated Code
*******************************************/
