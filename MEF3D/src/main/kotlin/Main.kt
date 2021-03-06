import classes.Mesh
import enums.Sizes.NODES
import tools.*

class Main{

    companion object {
        @JvmStatic fun main(args: Array<String>) {
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
            //println("\n")
            //b.Show()
            //println("*******************************")

            //Se aplica la condicion de Neumann
            applyNeumann(m, b)

            //Se aplica la condicion de dirichlet
            applyDirichlet(m, K, b)

            //Se calcula el resultado del SEL
            zeroes(w, b.size)
            calculate(K, b, w)
            println("Calculo terminado")
            //w.Show()


            writeResults(m, w, filename)
        }
    }

}