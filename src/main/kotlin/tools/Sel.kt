package tools

import classes.Condition
import classes.Element
import classes.Mesh
import classes.Node
import enums.Parameters.*
import enums.Sizes.*
import kotlin.math.pow


fun validateZero(n: Float) : Float{
    if(n < 0.0001f)
        return 0.0001f
    return n
}

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
    return validateZero((1.0f / validateZero((n2!!.x - n1!!.x).pow(2))))
}

fun calculatelocalC2(ind: Int, m: Mesh): Float {
    val el = m.getElement(ind)
    val n1 = m.getNode(el!!.node1 - 1)
    val n2 = m.getNode(el.node2 - 1)
    val n8 = m.getNode(el.node8 - 1)

    return validateZero((1.0f / validateZero((n2!!.x - n1!!.x))) * (4 * n1.x + 4 * n2.x - 8 * n8!!.x))
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

    println("a: $a b: $b c: $c d: $d e: $e f: $f g: $g h: $h i: $i")
    val J = a*e*i+d*h*c+g*b*f-g*e*c-a*h*f-d*b*i
    return J
}

fun calculateA(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    println("C1: $c1")
    val c2 = calculatelocalC2(el, m)
    println("C2: $c2")

    val a = (4* c1 - c2).pow(4)
    val b = (4* c1 - c2).pow(3)
    val c = (4* c1 - c2).pow(5)
    val d = (4* c1 + 3*c2).pow(5)

    val e = 1f/(192*c2.pow(2))
    val f = 1f/(24*c2)
    val g = 1f/(3840*c2.pow(3))

    return validateZero(-e*a - f*b - g*c + g*d)
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

    return validateZero(-e*a + f*b + g*c - g*d)
}

fun calculateC(el : Int, m: Mesh) : Float{
    val c2 = calculatelocalC2(el, m)

    return validateZero((4f/15f)*c2.pow(2))
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

    return validateZero(h*a - i*b + j*c - j*d + k*e - f*l + g*m)
}

fun calculateE(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return validateZero((8f/3f) *c1.pow(2) + (1f/30f)*c2.pow(2))
}

fun calculateF(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return validateZero((2f/3f) *c1*c2 - (1f/30f)*c2.pow(2))
}

fun calculateG(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return validateZero(-(16f/3f)*c1.pow(2) - (4f/3f)*c1*c2 - (2f/15f)*c2.pow(2))
}

fun calculateH(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return validateZero((2f/3f) *c1*c2 + (1f/30f)*c2.pow(2))
}

fun calculateI(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return validateZero(-(16f/3f)*c1.pow(2) - (2f/3f)*c2.pow(2))
}

fun calculateJ(el: Int, m: Mesh) : Float{

    val c2 = calculatelocalC2(el, m)

    return validateZero((2f/15f)*c2.pow(2))
}

fun calculateK(el: Int, m: Mesh) : Float{

    val c1 = calculatelocalC1(el, m)
    val c2 = calculatelocalC2(el, m)

    return validateZero(-(4f/3f)*c1*c2)
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


//Esta funcion crea los sitemas locales (K y b) y almacena los datos en sus respectivas listas
fun crearSistemasLocales(m: Mesh, localKs: ArrayList<Matrix>, localbs: ArrayList<Vector>) {
    for (i in 0 until m.getSize(ELEMENTS.ordinal)) {
        localKs.add(createLocalK(i, m))
        localbs.add(createLocalb(i, m))
    }
}

fun assemblyK(e: Element, localK: Matrix, K: Matrix, nnodes: Int) {
    val index1 = e.node1 - 1
    val index2 = e.node2 - 1
    val index3 = e.node3 - 1
    val index4 = e.node4 - 1
    val index5 = e.node5 - 1
    val index6 = e.node6 - 1
    val index7 = e.node7 - 1
    val index8 = e.node8 - 1
    val index9 = e.node9 - 1
    val index10 = e.node10 - 1

    _assemblyK(K, localK, index1, 0, e, nnodes)
    _assemblyK(K, localK, index2, 1, e, nnodes)
    _assemblyK(K, localK, index3, 2, e, nnodes)
    _assemblyK(K, localK, index4, 3, e, nnodes)
    _assemblyK(K, localK, index5, 4, e, nnodes)
    _assemblyK(K, localK, index6, 5, e, nnodes)
    _assemblyK(K, localK, index7, 6, e, nnodes)
    _assemblyK(K, localK, index8, 7, e, nnodes)
    _assemblyK(K, localK, index9, 8, e, nnodes)
    _assemblyK(K, localK, index10, 9, e, nnodes)

}

fun _assemblyK(K: Matrix, localK:Matrix, index: Int, i: Int, e : Element, nnodes: Int){

    val index1 = e.node1 - 1
    val index2 = e.node2 - 1
    val index3 = e.node3 - 1
    val index4 = e.node4 - 1
    val index5 = e.node5 - 1
    val index6 = e.node6 - 1
    val index7 = e.node7 - 1
    val index8 = e.node8 - 1
    val index9 = e.node9 - 1
    val index10 = e.node10 - 1


    //Alpha
    K[index][index1]  += localK[i][0]
    K[index][index2]  += localK[i][1]
    K[index][index3]  += localK[i][2]
    K[index][index4]  += localK[i][3]
    K[index][index5]  += localK[i][4]
    K[index][index6]  += localK[i][5]
    K[index][index7]  += localK[i][6]
    K[index][index8]  += localK[i][7]
    K[index][index9]  += localK[i][8]
    K[index][index10]  += localK[i][9]

    //beta
    K[index+nnodes][index1+nnodes]  += localK[i+10][10]
    K[index+nnodes][index2+nnodes]  += localK[i+10][11]
    K[index+nnodes][index3+nnodes]  += localK[i+10][12]
    K[index+nnodes][index4+nnodes]  += localK[i+10][13]
    K[index+nnodes][index5+nnodes]  += localK[i+10][14]
    K[index+nnodes][index6+nnodes]  += localK[i+10][15]
    K[index+nnodes][index7+nnodes]  += localK[i+10][16]
    K[index+nnodes][index8+nnodes]  += localK[i+10][17]
    K[index+nnodes][index9+nnodes]  += localK[i+10][18]
    K[index+nnodes][index10+nnodes]  += localK[i+10][19]

    //gamma
    K[index+nnodes*2][index1+nnodes*2]  += localK[i+20][20]
    K[index+nnodes*2][index2+nnodes*2]  += localK[i+20][21]
    K[index+nnodes*2][index3+nnodes*2]  += localK[i+20][22]
    K[index+nnodes*2][index4+nnodes*2]  += localK[i+20][23]
    K[index+nnodes*2][index5+nnodes*2]  += localK[i+20][24]
    K[index+nnodes*2][index6+nnodes*2]  += localK[i+20][25]
    K[index+nnodes*2][index7+nnodes*2]  += localK[i+20][26]
    K[index+nnodes*2][index8+nnodes*2]  += localK[i+20][27]
    K[index+nnodes*2][index9+nnodes*2]  += localK[i+20][28]
    K[index+nnodes*2][index10+nnodes*2]  += localK[i+20][29]

}

fun assemblyb(e: Element, localb: Vector, b: Vector, nnodes: Int) {
    val index1 = e.node1 - 1
    val index2 = e.node2 - 1
    val index3 = e.node3 - 1
    val index4 = e.node4 - 1
    val index5 = e.node5 - 1
    val index6 = e.node6 - 1
    val index7 = e.node7 - 1
    val index8 = e.node8 - 1
    val index9 = e.node9 - 1
    val index10 = e.node10 - 1

    //alpha
    b[index1]  += localb[0]
    b[index2]  += localb[1]
    b[index3]  += localb[2]
    b[index4]  += localb[3]
    b[index5]  += localb[4]
    b[index6]  += localb[5]
    b[index7]  += localb[6]
    b[index8]  += localb[7]
    b[index9]  += localb[8]
    b[index10]  += localb[9]

    //betha
    b[index1+nnodes]  += localb[10]
    b[index2+nnodes]  += localb[11]
    b[index3+nnodes]  += localb[12]
    b[index4+nnodes]  += localb[13]
    b[index5+nnodes]  += localb[14]
    b[index6+nnodes]  += localb[15]
    b[index7+nnodes]  += localb[16]
    b[index8+nnodes]  += localb[17]
    b[index9+nnodes]  += localb[18]
    b[index10+nnodes]  += localb[19]

    //gamma
    b[index1+nnodes*2]  += localb[20]
    b[index2+nnodes*2]  += localb[21]
    b[index3+nnodes*2]  += localb[22]
    b[index4+nnodes*2]  += localb[23]
    b[index5+nnodes*2]  += localb[24]
    b[index6+nnodes*2]  += localb[25]
    b[index7+nnodes*2]  += localb[26]
    b[index8+nnodes*2]  += localb[27]
    b[index9+nnodes*2]  += localb[28]
    b[index10+nnodes*2]  += localb[29]
}

fun ensamblaje(m: Mesh, localKs: ArrayList<Matrix>, localbs: ArrayList<Vector>, K: Matrix, b: Vector) {
    for (i in 0 until m.getSize(ELEMENTS.ordinal)) {
        val e = m.getElement(i)
        val nnodes = m.getSize(NODES.ordinal)
        if (e != null) {
            assemblyK(e, localKs[i], K, nnodes)
        }
        if (e != null) {
            assemblyb(e, localbs[i], b, nnodes)
        }
    }
}

fun applyNeumann(m: Mesh, b: Vector) {
    for (i in 0 until m.getSize(NEUMANN.ordinal)) {
        val c: Condition? = m.getCondition(i, NEUMANN)
        if (c != null) {
            b[c.node1- 1] = b[c.node1 - 1] + c.value_x
        }
    }
}

fun applyDirichlet(m: Mesh, K: Matrix, b: Vector) {
    val n = m.getSize(DIRICHLET.ordinal)
    for (i in 0 until m.getSize(DIRICHLET.ordinal)) {
        val c = m.getCondition(i, DIRICHLET)
        val index: Int = c!!.node1 - 1
        K.removeAt(index)
        b.removeAt(index)
        for (row in 0 until K.size) {
            val cell = K[row][index]
            K[row].removeAt(index)
            if(i < n/3)
                b[row] = b[row] + -1 * c.value_x * cell
            if(i < (n/3)*2)
                b[row] = b[row] + -1 * c.value_y * cell
            if(i <(n/3)*3)
                b[row] = b[row] + -1 * c.value_z * cell
        }
    }
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