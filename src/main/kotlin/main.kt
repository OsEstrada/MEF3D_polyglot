import classes.Mesh
import enums.Sizes.NODES
import tools.*

fun main(args: Array<String>) {
    //Se guarda el nombre del archivo obtenido de los argumentos pasados al programa
    val filename = args[0]

    val localKs: ArrayList<Matrix> = ArrayList()
    val localbs: ArrayList<Vector> = ArrayList()
    val K = Matrix()
    val b = Vector()
    val T = Vector()

    //Presentacion al programa

    //Presentacion al programa
    println("IMPLEMENTACION DEL METODO DE LOS ELEMENTOS FINITOS")
    println("""	- TRANSFERENCIA DE CALOR
	- 2 DIMENSIONES"""
    )
    println("""	- FUNCIONES DE FORMA LINEALES
	- PESOS DE GALERKIN"""
    )
    println("\t- MALLA TRIANGULAR IRREGULAR")
    println("*************************************************************************")


    //Se instancia el objeto de la clase Mesh
    val m = Mesh()

    //Se leen los datos del archivo .dat
    leerMallayCondiciones(m, filename)
    println("Datos obtenidos correctamente\n***********************")

    //Se crean los sitemas locales y se muestran
    crearSistemasLocales(m, localKs, localbs)
    showKs(localKs); showbs(localbs)
    println("*******************************")


    //Las matrices K y b se llenan de 0 y posteriormente se realiza el ensamblaje
    zeroes(K, m.getSize(NODES.ordinal)*3) //Se multiplica por 3 debido a que son 3 componentes vectoriales alpha beta y gamma
    zeroes(b, m.getSize(NODES.ordinal)*3)
    ensamblaje(m, localKs, localbs, K, b)
    K.Show()
    println("\n")
    b.Show()
    println("*******************************")

    //Se aplica la condicion de Neumann
    applyNeumann(m, b)
    println("*******************************")


    //Se aplica la condicion de dirichlet
    applyDirichlet(m, K, b)
    println("*******************************")

    //Se calcula el resultado del SEL
    zeroes(T, b.size)
    calculate(K, b, T)
    println("Calculo terminado")
    T.Show()
}

