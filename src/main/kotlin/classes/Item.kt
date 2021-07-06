package classes

open abstract class Item {
    var id : Int = 0

    var x : Double = 0.0

    var y : Double = 0.0

    var z : Double = 0.0

    var node1 : Int = 0

    var node2 : Int = 0

    var node3 : Int = 0

    var node4 : Int = 0

    var node5 : Int = 0

    var node6 : Int = 0

    var node7 : Int = 0

    var node8 : Int = 0

    var node9 : Int = 0

    var node10 : Int = 0

    var value_x : Double = 0.0

    var value_y : Double = 0.0

    var value_z : Double = 0.0


    abstract fun setValues(a: Int, b: Double, c: Double, d: Double, n1: Int, n2: Int, n3: Int, n4: Int, n5: Int, n6: Int,
                           n7: Int, n8: Int, n9: Int, n10: Int, i: Double)
}