package classes

//Metodo que nos ayudara a crear las listas de nodos
fun createNodes(n: Int): Array<Node?> {
    val list: Array<Node?> = arrayOfNulls(n)
    for (i in 0 until n) {
        list[i] = Node()
    }
    return list
}
//Metodo que nos ayudara a crear las listas de condiciones
fun createConditions(n: Int): Array<Condition?> {
    val list: Array<Condition?> = arrayOfNulls(n)
    for (i in 0 until n) {
        list[i] = Condition()
    }
    return list
}

//Metodo que nos ayudara a crear las listas de elementos
fun createElements(n: Int): Array<Element?> {
    val list: Array<Element?> = arrayOfNulls(n)
    for (i in 0 until n) {
        list[i] = Element()
    }
    return list
}