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
    val w = Vector()

    //Se instancia el objeto de la clase Mesh
    val m = Mesh()

    //Se leen los datos del archivo .dat
    leerMallayCondiciones(m, filename)
    println("Datos obtenidos correctamente\n***********************")

    //Se crean los sitemas locales y se muestran
    crearSistemasLocales(m, localKs, localbs)
    //showKs(localKs); showbs(localbs)
    println("*******************************")


    //Las matrices K y b se llenan de 0 y posteriormente se realiza el ensamblaje
    zeroes(K, m.getSize(NODES.ordinal)*3) //Se multiplica por 3 debido a que son 3 componentes vectoriales alpha beta y gamma
    zeroes(b, m.getSize(NODES.ordinal)*3)
    ensamblaje(m, localKs, localbs, K, b)
    //K.Show()
    println("\n")
    b.Show()
    println("*******************************")

    //Se aplica la condicion de Neumann
    applyNeumann(m, b)

    //Se aplica la condicion de dirichlet
    applyDirichlet(m, K, b)

    //Se calcula el resultado del SEL
    zeroes(w, b.size)
    calculate(K, b, w)
    println("Calculo terminado")
    w.Show()

/*    var M = Matrix(3, 3 ,0.0)
    M[0][0] = 6.0
    M[0][1] = 0.0
    M[0][2] = 2.0
    M[1][0] = 1.0
    M[1][1] = 3.0
    M[1][2] = 7.0
    M[2][0] = 1.0
    M[2][1] = 3.0
    M[2][2] = 0.0

    M.Show()

    val Inv = Matrix()

    inverseMatrixGauss(M, Inv);*/

}

