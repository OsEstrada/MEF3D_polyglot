package classes

class Condition : Item() {

    //Se reciben 4 datos, el numero del elemento y el valor, si es dirichlet recibira valores, sino recibira solo uno y los otros dos seran 0
    override fun setValues(
        a: Int,
        b: Float,
        c: Float,
        d: Float,
        n1: Int,
        n2: Int,
        n3: Int,
        n4: Int,
        n5: Int,
        n6: Int,
        n7: Int,
        n8: Int,
        n9: Int,
        n10: Int,
        i: Float
    ) {
        node1 = a
        value_x = b
        value_y = c
        value_z = d
    }
}