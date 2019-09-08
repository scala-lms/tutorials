/*****************************************
Emitting Generated Code
*******************************************/
class Snippet extends ((Array[Double])=>(Array[Double])) {
  def apply(x0:Array[Double]): Array[Double] = {
    val x1 = x0.length
    val x2 = new Array[Double](x1)
    val x3 = x1 - 2
    val x57 = x3 > 2
    val x151 = if (x57) {
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
      val x84 = x1 - 4
      val x85 = x84 / 2
      var x87 : Int = 0
      val x149 = while (x87 < x85) {
        // unrolled for k=0
        val x88 = 2 * x87
        val x91 = x88 + 3
        val x92 = x91 < x3
        val x118 = if (x92) {
          // variable reads
          val x94 = x78
          val x95 = x79
          val x96 = x80
          val x97 = x81
          val x98 = x82
          val x99 = x83
          // computation
          val x101 = x88 + 5
          val x102 = x0(x101)
          val x103 = x94 * x102
          val x104 = x94 - x103
          val x105 = x104 + x95
          val x106 = x96 * x105
          val x107 = x96 - x106
          val x108 = x107 + x97
          val x109 = x2(x98) = x108
          // variable writes
          x78 = x102
          x79 = x103
          x80 = x105
          x81 = x106
          x82 = x99
          x83 = x101
          ()
        } else {
          ()
        }
        // unrolled for k=1
        val x120 = x88 + 4
        val x121 = x120 < x3
        val x147 = if (x121) {
          // variable reads
          val x123 = x78
          val x124 = x79
          val x125 = x80
          val x126 = x81
          val x127 = x82
          val x128 = x83
          // computation
          val x130 = x88 + 6
          val x131 = x0(x130)
          val x132 = x123 * x131
          val x133 = x123 - x132
          val x134 = x133 + x124
          val x135 = x125 * x134
          val x136 = x125 - x135
          val x137 = x136 + x126
          val x138 = x2(x127) = x137
          // variable writes
          x78 = x131
          x79 = x132
          x80 = x134
          x81 = x135
          x82 = x128
          x83 = x130
          ()
        } else {
          ()
        }
        x87 = x87 + 1
      }
      x149
    } else {
      ()
    }
    x2
  }
}
/*****************************************
End of Generated Code
*******************************************/
