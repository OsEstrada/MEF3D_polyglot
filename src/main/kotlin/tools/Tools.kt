package tools

import classes.*

import enums.Lines.*
import enums.Lines
import enums.Sizes
import enums.Sizes.*
import enums.Modes.*
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
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
        val r: Float = values[1].trim { it <= ' ' }.toFloat()
        val rr: Float = values[2].trim { it <= ' ' }.toFloat()
        val rrr: Float = values[2].trim {it <= ' '}.toFloat()
        println("""$e     $r     $rr""")
        itemList[i]?.setValues(e, r, rr, rrr, 0, 0, 0,0,0,0,0,0,0,0,0f)
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
        itemList[i]?.setValues(e1, 0f, 0f, 0f, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, 0f)
    }
}

@Throws(IOException::class)
fun obtenerDatos(file: BufferedReader, nlines: Lines, n: Int, itemList: Array<Condition?>, type: Sizes) {
    var line: String
    line = file.readLine()
    line = file.readLine()
    var values: Array<String>? = null
    if (nlines === DOUBLELINE) line = file.readLine()
    for (i in 0 until n) {
        if (type == DIRICHLET && (i == n/3 || i == (n/3)*2)){
            line = file.readLine()
            line = file.readLine()
            line = file.readLine()
        }
        line = file.readLine()
        values = line.split("\\s+".toRegex()).toTypedArray()
        val e0: Int = values[0].trim { it <= ' ' }.toInt()
        val r0: Float = values[1].trim { it <= ' ' }.toFloat()
        println(e0.toString() + "\t" + r0)
        itemList[i]?.setValues(e0,r0,0f,0f,0,0,0,0,0,0,0,0,0,0,0f)
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
    var ei: Float
    var fx: Float
    var fy: Float
    var fz: Float
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
                ei = values[0].trim { it <= ' ' }.toFloat()
                fx = values[1].trim { it <= ' ' }.toFloat()
                fy = values[2].trim { it <= ' ' }.toFloat()
                fz = values[3].trim { it <= ' ' }.toFloat()
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
                obtenerDatos(file, DOUBLELINE, ndirich, m.getDirichlet(), DIRICHLET)
                println("\nNeumann")
                obtenerDatos(file, DOUBLELINE, nneu, m.getNeumann(), NEUMANN)

                //Se corrigen los indices en base a las filas que seran eliminadas luego de aplicar dirichlet
                //Tools.Tools.correctConditions(ndirich, m.getDirichlet(), m.getDirichletIndices())
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
