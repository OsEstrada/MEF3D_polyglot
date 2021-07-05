package tools

import java.io.BufferedReader
import java.io.IOException

@Throws(IOException::class)
fun obtenerDatos(file: BufferedReader, nlines: Lines, n: Int, mode: Modes?, itemList: Array<Item>) {
    var line: String
    line = file.readLine()
    line = file.readLine()
    var values: Array<String>? = null
    if (nlines === DOUBLELINE) line = file.readLine()
    for (i in 0 until n) {
        when (mode) {
            INT_FLOAT -> {
                var e0: Int
                var r0: Float
                line = file.readLine()
                values = line.split("\\s+".toRegex()).toTypedArray()
                e0 = values[0].trim { it <= ' ' }.toInt()
                r0 = values[1].trim { it <= ' ' }.toFloat()
                println(e0.toString() + "\t" + r0)
                itemList[i].setValues(0, 0, 0, e0, 0, 0, r0)
            }
            INT_FLOAT_FLOAT -> {
                var e: Int
                var r: Float
                var rr: Float
                line = file.readLine()
                values = line.split("\\s+".toRegex()).toTypedArray()
                e = values[0].trim { it <= ' ' }.toInt()
                r = values[1].trim { it <= ' ' }.toFloat()
                rr = values[2].trim { it <= ' ' }.toFloat()
                println(e.toString() + "\t" + r + "\t" + rr)
                itemList[i].setValues(e, r, rr, 0, 0, 0, 0)
            }
            INT_INT_INT_INT -> {
                var e1: Int
                var e2: Int
                var e3: Int
                var e4: Int
                line = file.readLine()
                values = line.split("\\s+".toRegex()).toTypedArray()
                e1 = values[0].trim { it <= ' ' }.toInt()
                e2 = values[1].trim { it <= ' ' }.toInt()
                e3 = values[2].trim { it <= ' ' }.toInt()
                e4 = values[3].trim { it <= ' ' }.toInt()
                println(e1.toString() + "\t" + e2 + "\t" + e3 + "\t" + e4)
                itemList[i].setValues(e1, 0, 0, e2, e3, e4, 0)
            }
        }
    }
}