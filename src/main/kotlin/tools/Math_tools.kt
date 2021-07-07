package tools

import kotlin.math.*
import kotlin.system.exitProcess

//Funciones sobrecargadas que llenan de 0 matrices y vectores respectivamente.
fun zeroes(M: Matrix, n: Int) {
    for (i in 0 until n) {
        M.add(Vector(n, 0.0))
    }
}

fun zeroes(M: Matrix, n: Int, m: Int) {
    for (i in 0 until n) {
        M.add(Vector(m, 0.0))
    }
}

fun zeroes(v: Vector, n: Int) {
    for (i in 0 until n) {
        v.add(0.0)
    }
}

//Funcion que copia la informacion de una matriz A a una matriz copy
fun copyMatrix(A: Matrix, copy: Matrix) {
    zeroes(copy, A.size)
    for (i in 0 until A.size -1) {
        for (j in 0 until A[0].size - 1) {
            copy[i][j] = A[i][j]
        }
    }
}

fun calculateMember(i: Int, j: Int, r: Int, A: Matrix, B: Matrix): Double {
    var member = 0.0
    for (k in 0 until r) {
        member += A[i][k] * B[k][j]
    }
    return member
}

//Metodo estatico que calcula el producto de dos matrices
fun productMatrixMatrix(A: Matrix, B: Matrix, n: Int, r: Int, m: Int): Matrix? {
    //Instancia la matriz y la llena de ceros
    var R = Matrix(n, m, 0.0)
    for (i in 0 until n) {
        for (j in 0 until m) {
            R[i][j] = calculateMember(i, j, r, A, B)
        }
    }
    return R
}

//Metodo estatico que calcula el producto de una matriz A por un vector v
fun productMatrixVector(A: Matrix, v: Vector, R: Vector) {
    for (i in 0 until A.size) {
        var cell = 0.0
        for (j in 0 until v.size) {
            cell += A[i][j] * v[j]
        }
        R[i] += cell
    }
}

//Metodo estatico que calcula el producto de un numero real por una matriz
fun productRealMatrix(real: Double, M: Matrix, R: Matrix) {
    zeroes(R, M.size)
    for (i in 0 until M.size) {
        for (j in 0 until M[0].size) {
            R[i][j] = real * M[i][j]
        }
    }
}

//Metodo que calcula el menor de una matriz M
fun getMinor(M: Matrix, i: Int, j: Int) {
    M.removeAt(i)
    for (k in 0 until M.size) {
        M[k].removeAt(j)
    }
}

//Funcion que calcula el determinante de una matriz M
fun determinant(M: Matrix): Double {
    return if (M.size == 1) M[0][0] else {
        var det = 0.0
        for (i in 0 until M.get(0).size) {
            var minor = Matrix()
            copyMatrix(M, minor)
            getMinor(minor, 0, i)
            det += (-1.0).pow(i) * M[0][i] * determinant(minor)
        }
        return det
    }
}

//Metodo que calcula la matriz de cofactores dada una matriz M
fun cofactors(M: Matrix, Cof: Matrix) {
    zeroes(Cof, M.size)
    for (i in 0 until M.size) {
        for (j in 0 until M[0].size) {
            var minor = Matrix()
            copyMatrix(M, minor)
            getMinor(minor, i, j)
            Cof[i][j] = (-1.0).pow((i + j)) * determinant(minor)
        }
    }
}

//Metodo que calcula la matriz transpuesta dada una matriz M
fun transpose(M: Matrix, T: Matrix) {
    zeroes(T, M[0].size, M.size)
    for (i in 0 until M.size) {
        for (j in 0 until M[0].size) {
            T[j][i] = M[i][j]
        }
    }
}

//Metodo que calcula la inversa de una matriz M utilizando el metodo de la adjunta.
fun inverseMatrix(M: Matrix, Minv: Matrix) {
    println("Iniciando Calculo de inversa ....")
    val Cof = Matrix()
    val Adj = Matrix()
    println("Calculo de determinante...")
    val det = determinant(M)
    if (det == 0.0) exitProcess(1)
    println("Iniciando calculo de cofactores...")
    cofactors(M, Cof)
    println("Calculo de Adjunta...")
    transpose(Cof, Adj)
    println("Calculo de inversa...")
    productRealMatrix(1.0f / det, Adj, Minv)
}

//ACA SE UBICA EL CODIGO PARA CALCULAR LA INVERSA USANDO GAUSS JORDAN

fun matrizIdentidad(r : Int, c: Int) : Matrix{
    val M  = Matrix(r,c,0.0)
    for (i in 0 until M.size){
        M[i][i] = 1.0
    }

    return M
}

fun switchRows(M: Matrix, r1: Int, r2: Int) {
    var aux: Double
    for (i in 0 until M[0].size) {
        aux = M[r1][i]
        M[r1][i] = M[r2][i]
        M[r2][i] = aux
    }
}

fun multiplyRowConstant(M: Matrix, r: Int, c: Double) {
    for (i in 0 until M[0].size) {
        M[r][i] *= c
    }
}

fun addRows(M: Matrix, r1: Int, r2: Int, c: Double) {
    for (i in 0 until M[0].size) {
        M[r1][i] += M[r2][i] * c
    }
}

fun gaussjordan(M: Matrix): Matrix {
    val Inv: Matrix = matrizIdentidad(M.size, M[0].size)
    for (i in 0 until M.size) {
        if (M[i][i] == 0.0) {
            var r = 0
            while (r < M[0].size-1 && (M[i][r] == 0.0 || M[r][i] == 0.0)) {
                r++
            }
            println("i $i , r $r")
            switchRows(M, i, r)
        }
        var c: Double = 1.0 / M[i][i]
        multiplyRowConstant(M, i, c)
        multiplyRowConstant(Inv, i, c)
        for (j in 0 until M.size) {
            if (i != j) {
                c = -M[j][i]
                addRows(M, j, i, c)
                addRows(Inv, j, i, c)
            }
        }
    }
    return Inv
}

fun inverseMatrixGauss(M: Matrix, Minv: Matrix) {
    println("Calculo de determinante...")
    //val det = determinant(M)
    //if (det == 0.0) exitProcess(1)
    //println("determinante: $det")
    println("Calculando inversa...")
    var m = gaussjordan(M)
    m.Show()
    copyMatrix(gaussjordan(M), Minv)

}