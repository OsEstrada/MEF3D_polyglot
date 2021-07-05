import classes.Mesh
import tools.Matrix
import tools.Vector
import tools.*

fun main(args: Array<String>) {
    //Se guarda el nombre del archivo obtenido de los argumentos pasados al programa
    val filename = args[0]

    val localKs: ArrayList<Matrix> = ArrayList<Matrix>()
    val localbs: ArrayList<Vector> = ArrayList<Vector>()
    val K = Matrix()
    val b = Vector()
    val T = Vector()

    //Presentacion al programa

    //Presentacion al programa
    println("IMPLEMENTACION DEL METODO DE LOS ELEMENTOS FINITOS")
    println(
        """	- TRANSFERENCIA DE CALOR
	- 2 DIMENSIONES"""
    )
    println(
        """	- FUNCIONES DE FORMA LINEALES
	- PESOS DE GALERKIN"""
    )
    println("\t- MALLA TRIANGULAR IRREGULAR")
    println("*************************************************************************")


    //Se instancia el objeto de la clase Mesh
    val m = Mesh()

    //Se leen los datos del archivo .dat
    leerMallayCondiciones(m, filename)
    println("Datos obtenidos correctamente\n***********************")

}

