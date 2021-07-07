package classes

import enums.Parameters.*
import enums.Sizes
import enums.Sizes.*

class Mesh {
    private var parameters = DoubleArray(4)
    private var sizes = IntArray(4)
    private lateinit var indices_dirich: IntArray
    private lateinit var node_list: Array<Node?>
    private lateinit var element_list: Array<Element?>
    private lateinit var dirichlet_list: Array<Condition?>
    private lateinit var neumann_list: Array<Condition?>

    fun setParameters(ei: Double, f_x: Double, f_y: Double, f_z: Double) {
        parameters[EI.ordinal] = ei
        parameters[FORCE_X.ordinal] = f_x
        parameters[FORCE_Y.ordinal] = f_y
        parameters[FORCE_Z.ordinal] = f_z
    }

    fun setSizes(nnodes: Int, neltos: Int, ndirich: Int, nneu: Int) {
        sizes[NODES.ordinal] = nnodes
        sizes[ELEMENTS.ordinal] = neltos
        sizes[DIRICHLET.ordinal] = ndirich
        sizes[NEUMANN.ordinal] = nneu
    }

    fun getSize(s: Int): Int {
        return sizes[s]
    }

    fun getParameter(p: Int): Double {
        return parameters[p]
    }

    //Crea inicializan los arreglos correspondientes a la lista de nodos, elementos, y condiciones de contorno
    fun createData() {
        /*En este punto todos los arreglos que guardan objetos tienen elementos nulos. Entonces hay que llenarlas con nuevas instancias,
          Para eso se creo un metodo estatico en las clases Node, Element y Condition que generan un arreglo lleno de objetos el cual es
          el que trabajaremos
        */
        node_list = createNodes(sizes[NODES.ordinal])
        element_list = createElements(sizes[ELEMENTS.ordinal])
        indices_dirich = IntArray(sizes[DIRICHLET.ordinal])
        dirichlet_list = createConditions(sizes[DIRICHLET.ordinal])
        neumann_list = createConditions(sizes[NEUMANN.ordinal])
    }

    //Metodo que retorna el arreglo indices_dirich
    fun getDirichletIndices(): IntArray {
        return indices_dirich
    }

    //Metodo que retorna el arreglo node_list
    fun getNodes(): Array<Node?> {
        return node_list
    }

    //Metodo que retorna el arreglo element_list
    fun getElements(): Array<Element?> {
        return element_list
    }

    //Metodo que retorna el arreglo dirichlet_list
    fun getDirichlet(): Array<Condition?> {
        return dirichlet_list
    }

    //Metodo que retorna el arreglo neumann_list
    fun getNeumann(): Array<Condition?> {
        return neumann_list
    }

    //Metodo que retorna el nodo almacenado en el indice i
    fun getNode(i: Int): Node? {
        return node_list[i]
    }

    //Metodo que retorna el elemento en el indice i
    fun getElement(i: Int): Element? {
        return element_list[i]
    }

    //Metodo que regresa la condicion de contorno, almacenado en el indice i dependiendo del tipo recibido
    fun getCondition(i: Int, type: Sizes): Condition? {
        return if (type === DIRICHLET) dirichlet_list[i] else neumann_list[i]
    }

}