package tools

import classes.*
import enums.Lines
import enums.Lines.*
import enums.Modes.*
import enums.Sizes
import enums.Sizes.*
import java.awt.image.DirectColorModel
import java.io.*
import kotlin.system.exitProcess


@Throws(IOException::class)
fun obtenerDatos(file: BufferedReader, nlines: Lines, n: Int, itemList: Array<Node?>) {
    var line: String
    line = file.readLine()
    line = file.readLine()
    var values: Array<String>? = null
    if (nlines === DOUBLELINE) line = file.readLine()
    for (i in 0 until n) {
        line = file.readLine()
        values = line.split("\\s+".toRegex()).toTypedArray()
        val e: Int = values[0].trim { it <= ' ' }.toInt()
        val r: Double = values[1].trim { it <= ' ' }.toDouble()
        val rr: Double = values[2].trim { it <= ' ' }.toDouble()
        val rrr: Double = values[2].trim {it <= ' '}.toDouble()
        println("""$e     $r     $rr""")
        itemList[i]?.setValues(e, r, rr, rrr, 0, 0, 0,0,0,0,0,0,0,0,0.0)
    }
}


@Throws(IOException::class)
fun obtenerDatos(file: BufferedReader, nlines: Lines, n: Int, itemList: Array<Element?>) {
    var line: String
    line = file.readLine()
    line = file.readLine()
    var values: Array<String>? = null
    if (nlines === DOUBLELINE) line = file.readLine()
    for (i in 0 until n) {
        line = file.readLine()
        values = line.split("\\s+".toRegex()).toTypedArray()
        val e1: Int = values[0].trim { it <= ' ' }.toInt()
        val e2: Int = values[1].trim { it <= ' ' }.toInt()
        val e3: Int = values[2].trim { it <= ' ' }.toInt()
        val e4: Int = values[3].trim { it <= ' ' }.toInt()
        val e5: Int = values[4].trim { it <= ' ' }.toInt()
        val e6: Int = values[5].trim { it <= ' ' }.toInt()
        val e7: Int = values[6].trim { it <= ' ' }.toInt()
        val e8: Int = values[7].trim { it <= ' ' }.toInt()
        val e9: Int = values[8].trim { it <= ' ' }.toInt()
        val e10: Int = values[9].trim { it <= ' ' }.toInt()
        val e11: Int = values[10].trim { it <= ' ' }.toInt()
        println("""$e1     $e2     $e3     $e4     $e5     $e6     $e7     $e8     $e9     $e10     $e11""")
        itemList[i]?.setValues(e1, 0.0, 0.0, 0.0, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, 0.0)
    }
}

@Throws(IOException::class)
fun obtenerDatos(file: BufferedReader, nlines: Lines, n: Int, itemList: Array<Condition?>, type: Sizes, nnodes: Int) {
    var line: String
    var aux = 0
    var j = 0
    line = file.readLine()
    line = file.readLine()
    var values: Array<String>? = null
    if (nlines === DOUBLELINE) line = file.readLine()
    for (i in 0 until n) {
        if (type == DIRICHLET && (i == n/3 || i == (n/3)*2)){
            line = file.readLine()
            line = file.readLine()
            line = file.readLine()
            j++
            aux = nnodes*j
        }
        line = file.readLine()
        values = line.split("\\s+".toRegex()).toTypedArray()
        val e0: Int = values[0].trim { it <= ' ' }.toInt() + aux
        val r0: Double = values[1].trim { it <= ' ' }.toDouble()
        println(e0.toString() + "\t" + r0)
        itemList[i]?.setValues(e0,r0,0.0,0.0,0,0,0,0,0,0,0,0,0,0,0.0)
    }
}

//Funcion que concatena la extension y el nombre del archivo
fun addExtension(filename: String, extension: String): String {
    return filename + extension
}


//Funcion que lee los datos de la malla de un documento y los almacena en un objeto de la clase Mesh
fun leerMallayCondiciones(m: Mesh, filename: String) {
    var line: String
    var values: Array<String>
    var ei: Double
    var fx: Double
    var fy: Double
    var fz: Double
    var nnodes: Int
    var neltos: Int
    var ndirich: Int
    var nneu: Int
    val inputfilename = addExtension(filename, ".dat")

    //Se crea la conexion con el archivo. Cuando se termine de ejecutar la sentencia try catch la conexin
    //se cierra. El try catch es requerido para poder usar los objetos FileReader y BufferedReader
    try {
        FileReader(inputfilename).use { fr ->
            BufferedReader(fr).use { file ->
                line = file.readLine()
                values = line.split("\\s+".toRegex()).toTypedArray()
                ei = values[0].trim { it <= ' ' }.toDouble()
                fx = values[1].trim { it <= ' ' }.toDouble()
                fy = values[2].trim { it <= ' ' }.toDouble()
                fz = values[3].trim { it <= ' ' }.toDouble()
                println("EI $ei   Fx $fx    Fy $fy  Fz $fz")
                line = file.readLine()
                values = line.split("\\s+".toRegex()).toTypedArray()
                nnodes = values[0].trim { it <= ' ' }.toInt()
                neltos = values[1].trim { it <= ' ' }.toInt()
                ndirich = values[2].trim { it <= ' ' }.toInt()
                nneu = values[3].trim { it <= ' ' }.toInt()
                println(nnodes.toString() + "\t" + neltos + "\t" + ndirich + "\t" + nneu)
                m.setParameters(ei, fx, fy, fz)
                m.setSizes(nnodes, neltos, ndirich, nneu)
                m.createData()
                obtenerDatos(file, SINGLELINE, nnodes, m.getNodes())
                println("\nElements")
                obtenerDatos(file, DOUBLELINE, neltos, m.getElements())
                println("\nDirichlet")
                obtenerDatos(file, DOUBLELINE, ndirich, m.getDirichlet(), DIRICHLET, nnodes)
                println("\nNeumann")
                obtenerDatos(file, DOUBLELINE, nneu, m.getNeumann(), NEUMANN, 0)

                //Se corrigen los indices en base a las filas que seran eliminadas luego de aplicar dirichlet
                correctConditions(ndirich, m.getDirichlet(), m.getDirichletIndices())
            }
        }
    } catch (ex: IOException) {
        println(
            """
                Hubo un error al leer el archivo...
                ${ex.message}
                Saliendo del programa
                """.trimIndent()
        )
        exitProcess(1)
    }
}

fun correctConditions(n: Int, list: Array<Condition?>, indices: IntArray) {
    for (i in 0 until n) indices[i] = list[i]!!.node1
    for (i in 0 until n - 1) {
        val pivot: Int = list[i]!!.node1
        //Si la condicion corresponde a un nodo es posterior al pivote, se aplica la correcion al indice
        for (j in i until n) {
            if (list[j]!!.node1 > pivot){
                list[j]!!.node1 = list[j]!!.node1 - 1
            }
        }
    }
}

fun findIndex(v: Int, s: Int, arr: IntArray): Boolean {
    for (i in 0 until s) {
        if (arr[i] == v) return true
    }
    return false
}

//Metodo que escribe en un archivo los resultados del SEL
fun writeResults(m: Mesh, T: Vector, filename: String) {
    val dirichlet_indices = m.getDirichletIndices()
    val nnd = T.size/3
    val dirich = m.getDirichlet()
    val ndirich = m.getSize(DIRICHLET.ordinal)/3
    val outputfilename: String = addExtension(filename, ".post.res")
    try {
        FileWriter(outputfilename).use { fr ->
            BufferedWriter(fr).use { file ->
                file.write("GiD Post Results File 1.0\n")
                file.write("Result \"Displacement\" \"Load Case 1\" 1 Vector OnNodes\nComponentNames \"X-Displ\" \"Y-Displ\"\"Z-Displ\"\nValues\n")
                var Tpos = 0
                var Dpos = 0
                val n = m.getSize(NODES.ordinal)
                val nd = m.getSize(DIRICHLET.ordinal)
                for (i in 0 until n) {
                    if (findIndex(i + 1, nd, dirichlet_indices)) {
                        file.write(
                            "${i + 1} ${dirich[Dpos]!!.x} ${dirich[Dpos+ndirich]!!.x} ${dirich[Dpos+2*ndirich]!!.x}\n"                        )
                        Dpos++
                    } else {
                        file.write(
                            "${i + 1} ${T[Tpos]} ${T[Tpos+nnd]} ${T[Tpos+2*nnd]}\n"
                        )
                        Tpos++
                    }
                }
                file.write("End values\n")
            }
        }
    } catch (ex: IOException) {
        println("Hubo un error al escribir el archivo...\nSaliendo del programa")
        exitProcess(1)
    }
}