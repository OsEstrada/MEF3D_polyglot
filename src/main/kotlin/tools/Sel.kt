package tools

import classes.Mesh
import classes.Node
import enums.Parameters.*
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

fun calculateA(el: Int, m: Mesh) : Float{

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

fun calculateB(el: Int, m: Mesh) : Float{

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

fun calculateC(el : Int, m: Mesh) : Float{
    val c2 = calculatelocalC2(el, m)

    return (4f/15f)*c2.pow(2)
}

fun calculateD(el: Int, m: Mesh) : Float{

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

fun calculateE(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return (8f/3f)*c1.pow(2) + (1f/30f)*c2.pow(2)
}

fun calculateF(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return (2f/3f)*c1*c2 - (1f/30f)*c2.pow(2)
}

fun calculateG(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return -(16f/3f)*c1.pow(2) - (4f/3f)*c1*c2 - (2f/15f)*c2.pow(2)
}

fun calculateH(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return (2f/3f)*c1*c2 + (1f/30f)*c2.pow(2)
}

fun calculateI(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return -(16f/3f)*c1.pow(2) - (2f/3f)*c2.pow(2)
}

fun calculateJ(el: Int, m: Mesh) : Float{

    val c2 = calculatelocalC2(el, m)

    return (2f/15f)*c2.pow(2)
}

fun calculateK(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return -(4f/3f)*c1*c2
}

fun createLocalb(el : Int, m: Mesh) : Vector{
    val tau = floatArrayOf(59f,-1f,-1f,-1f,4f,4f,4f,4f,4f,4f)
    val mt = Matrix(30,3,0f)
    val J = calculateLocalJ(el, m)
    val f = Vector()
    f.add(m.getParameter(FORCE_X.ordinal))
    f.add(m.getParameter(FORCE_Y.ordinal))
    f.add(m.getParameter(FORCE_Z.ordinal))

    //Llenado de matriz
    for (i in tau.indices){
        mt[i][0] = J*tau[i]
        mt[10+i][1] = J*tau[i]
        mt[20+i][2] = J*tau[i]
    }

    var b  = Vector(30, 0f)
    productMatrixVector(mt, f, b)

    return b
}

fun createMu(el: Int, m: Mesh) : Matrix{
    val mu = Matrix(10,10,0f)
    val A = calculateA(el, m)
    val B = calculateB(el, m)
    val C = calculateC(el, m)
    val D = calculateD(el, m)
    val E = calculateE(el, m)
    val F = calculateF(el, m)
    val G = calculateG(el, m)
    val H = calculateH(el, m)
    val I = calculateI(el, m)
    val J = calculateJ(el, m)
    val K = calculateK(el, m)

    mu[0][0] = A; mu[1][0] = E; mu[4][0] = -F; mu[6][0] = -F; mu[7][0] = G; mu[8][0] = F; mu[9][0] = F //Primera col
    mu[0][1] = E; mu[1][1] = B; mu[4][1] = -H; mu[6][1] = -H; mu[7][1] = I; mu[8][1] = H; mu[9][1] = H //Segunda col
    mu[0][4] = -F; mu[1][4] = -H; mu[4][4] = C; mu[6][4] = J; mu[7][4] = -K; mu[8][4] = -C; mu[9][4] = -J //Quinta col
    mu[0][6] = -F; mu[1][6] = -H; mu[4][6] = J; mu[6][6] = C; mu[7][6] = -K; mu[8][6] = -J; mu[9][6] = -C //Septima col
    mu[0][7] = G; mu[1][7] = I; mu[4][7] = -K; mu[6][7] = -K; mu[7][7] = D; mu[8][7] = K; mu[9][7] = K //Octava col
    mu[0][8] = F; mu[1][8] = H; mu[4][8] = -C; mu[6][8] = -J; mu[7][8] = K; mu[8][8] = C; mu[9][8] = J //Novena col
    mu[0][9] = F; mu[1][9] = H; mu[4][9] = -J; mu[6][9] = -C; mu[7][9] = K; mu[8][9] = J; mu[9][9] = C //Decima col

    return mu
}

fun createLocalK(el : Int, m : Mesh) : Matrix{
    val k = Matrix(30,30,0f)
    val J = calculateLocalJ(el, m)
    val mu = createMu(el, m)
    val EI = m.getParameter(EI.ordinal)

    //Llenado matriz mt
    for (i in mu.indices){
        for (j in mu[0].indices){
            k[i][j] = EI*J*mu[i][j]
            k[10+i][10+j] = EI*J*mu[i][j]
            k[20+i][20+j] = EI*J*mu[i][j]
        }
    }

    return k
}



//Funcion que calcula el resultado del SEL
fun calculate(K: Matrix?, b: Vector?, T: Vector?) {
    println("Iniciando calculo de respuesta...")
    val Kinv = Matrix()
    println("Calculo de inversa...")
    inverseMatrix(K!!, Kinv)
    println("Calculo de respuesta...")
    productMatrixVector(Kinv, b!!, T!!)
}