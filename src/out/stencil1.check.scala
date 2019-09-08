/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((Array[Double])=>(Array[Double])) {
  def apply(x0:Array[Double]): Array[Double] = {
    val x1 = x0.length
    val x2 = new Array[Double](x1)
    val x3 = x1 - 2
    val x48 = x3 > 2
    val x105 = if (x48) {
      val x49 = x0(2)
      val x50 = x0(3)
      val x51 = x49 * x50
      val x52 = x49 - x51
      val x53 = x0(1)
      val x54 = x53 * x49
      val x55 = x52 + x54
      val x56 = x0(4)
      val x57 = x50 * x56
      val x58 = x50 - x57
      val x59 = x58 + x51
      val x60 = x55 * x59
      val x61 = x55 - x60
      val x62 = x53 - x54
      val x63 = x0(0)
      val x64 = x63 * x53
      val x65 = x62 + x64
      val x66 = x65 * x55
      val x67 = x61 + x66
      val x68 = x2(2) = x67
      var x69: Double = x56
      var x70: Double = x57
      var x71: Double = x59
      var x72: Double = x60
      var x73: Int = 3
      var x74: Int = 4
      var x76 : Int = 3
      val x103 = while (x76 < x3) {
        // variable reads
        val x78 = x69
        val x79 = x70
        val x80 = x71
        val x81 = x72
        val x82 = x73
        val x83 = x74
        // computation
        val x86 = x76 + 2
        val x87 = x0(x86)
        val x88 = x78 * x87
        val x89 = x78 - x88
        val x90 = x89 + x79
        val x91 = x80 * x90
        val x92 = x80 - x91
        val x93 = x92 + x81
        val x94 = x2(x82) = x93
        // variable writes
        x69 = x87
        x70 = x88
        x71 = x90
        x72 = x91
        x73 = x83
        x74 = x86
        x76 = x76 + 1
      }
      x103
    } else {
      ()
    }
    x2
  }
}
/*****************************************
End of Generated Code
*******************************************/
