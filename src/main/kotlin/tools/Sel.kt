package tools

import classes.Mesh
import classes.Node
import kotlin.math.pow

fun showKs(Ks: ArrayList<Matrix>) {
    for (i in Ks.indices) {
        println("K del elemento" + (i + 1))
        Ks[i].Show()
        println("**********************************")
    }
}

//Muestra ls bs
fun showbs(bs: ArrayList<Vector>) {
    for (i in bs.indices) {
        println("b del elemento" + (i + 1))
        bs[i].Show()
        println("**********************************")
    }
}

fun calculateLocalVolume(ind: Int, m: Mesh): Float {

    //Declaracion de variables a usar
    val el = m.getElement(ind)
    val n1: Node? = m.getNode(el!!.node1 - 1)
    val n2: Node? = m.getNode(el.node2 - 1)
    val n3: Node? = m.getNode(el.node3 - 1)
    val n4: Node? = m.getNode(el.node4 - 1)

    val a = n2!!.x - n1!!.x
    val b = n2.y - n1.y
    val c = n2.z - n1.z
    val d = n3!!.x - n1.x
    val e = n3.y - n1.y
    val f = n3.z - n1.z
    val g = n4!!.x - n1.x
    val h = n4.y - n1.y
    val i = n4.z - n1.z

    return (1.0f / 6.0f) * (a * e * i + d * h * c + g * b * f - g * e * c - a * h * f - d * b * i)
}

fun calculatelocalC1(ind: Int, m: Mesh): Float {
    val el = m.getElement(ind)
    val n1 = m.getNode(el!!.node1 - 1)
    val n2 = m.getNode(el.node2 - 1)

    return (1.0f / (n2!!.x - n1!!.x).pow(2))
}

fun calculatelocalC2(ind: Int, m : Mesh) : Float{
    val el = m.getElement(ind)
    val n1 = m.getNode(el!!.node1 - 1)
    val n2 = m.getNode(el.node2 - 1)
    val n8 = m.getNode(el.node8 - 1)

    return (1.0f / (n2!!.x - n1!!.x)) * (4*n1.x + 4*n2.x - 8*n8!!.x)
}

fun calculateLocalJ(ind: Int, m: Mesh): Float {
    val el = m.getElement(ind)

    val n1: Node? = m.getNode(el!!.node1 - 1)
    val n2: Node? = m.getNode(el.node2 - 1)
    val n3: Node? = m.getNode(el.node3 - 1)
    val n4: Node? = m.getNode(el.node4 - 1)

    val a = n2!!.x - n1!!.x
    val b = n3!!.x - n1.x
    val c = n4!!.x - n1.x
    val d = n2.y - n1.y
    val e = n3.y - n1.y
    val f = n4.y - n1.y
    val g = n2.z - n1.z
    val h = n3.z - n1.z
    val i = n4.z - n1.z

    return a * e * i + d * h * c + g * b * f - g * e * c - a * h * f - d * b * i
}

fun calculateLocalA(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    val a = (4* c1 - c2).pow(4)
    val b = (4* c1 - c2).pow(3)
    val c = (4* c1 - c2).pow(5)
    val d = (4* c1 + 3*c2).pow(5)

    val e = 1f/(192*c2.pow(2))
    val f = 1f/(24*c2)
    val g = 1f/(3840*c2.pow(3))

    return -e*a - f*b - g*c + g*d
}

fun calculateLocalB(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    val a = (4* c1 + c2).pow(4)
    val b = (4* c1 + c2).pow(3)
    val c = (4* c1 + c2).pow(5)
    val d = (4* c1 - 3*c2).pow(5)

    val e = 1f/(192*c2.pow(2))
    val f = 1f/(24*c2)
    val g = 1f/(3840*c2.pow(3))

    return -e*a + f*b + g*c - g*d
}

fun calculateLocalC(el : Int, m: Mesh) : Float{
    val c2 = calculatelocalC2(el, m)

    return (4f/15f)*c2.pow(2)
}

fun calculateLocalD(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    val a = (4* c2 - c1).pow(4)
    val b = (4* c2 - c1).pow(5)
    val c = (4* c2 + 8*c1).pow(5)
    val d = (4* c2 - 8*c1).pow(5)
    val e = (-8*c1).pow(5)
    val f = (4*c2 - 8*c1).pow(4)
    val g = (-8*c1).pow(4)

    val h = 1f/(192*c2.pow(2))
    val i = 1f/(3840*c2.pow(3))
    val j = 1f/(7680*c2.pow(3))  //Aplica para el 3er y 4to termino
    val k = 1f/(768*c2.pow(3))
    val l = c1/(96*c2.pow(3))
    val m = (2*c1 - 1)/(192*c2.pow(3))

    return h*a - i*b + j*c - j*d + k*e - f*l + g*m
}

fun calculateLocalE(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return (8f/3f)*c1.pow(2) + (1f/30f)*c2.pow(2)
}

fun calculateLocalF(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return (2f/3f)*c1*c2 - (1f/30f)*c2.pow(2)
}

fun calculateLocalG(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return -(16f/3f)*c1.pow(2) - (4f/3f)*c1*c2 - (2f/15f)*c2.pow(2)
}

fun calculateLocalH(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return -(16f/3f)*c1.pow(2) - (4f/3f)*c1*c2 - (2f/15f)*c2.pow(2)
}

fun calculateLocalI(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return -(16f/3f)*c1.pow(2) - (2f/3f)*c2.pow(2)
}
