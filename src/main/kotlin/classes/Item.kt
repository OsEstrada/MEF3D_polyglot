package classes

abstract class Item {
    var id : Int = 0

    var x : Float = 0.0f

    var y : Float = 0.0f

    var z : Float = 0.0f

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

    var value_x : Float = 0.0f

    var value_y : Float = 0.0f

    var value_z : Float = 0.0f


    abstract fun setValues(a: Int, b: Float, c: Float, d: Float, n1: Int, n2: Int, n3: Int, n4: Int, n5: Int, n6: Int,
                           n7: Int, n8: Int, n9: Int, n10: Int, i: Float)
}