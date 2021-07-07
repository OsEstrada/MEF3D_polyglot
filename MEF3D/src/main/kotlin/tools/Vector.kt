package tools

class Vector () : ArrayList<Double>() {

    //Constructor que servira para inicializar en 0 un vector recien instanciado
    constructor(size: Int, defaultElement: Double) : this(){
        for (i in 0 until size) {
            this.add(defaultElement)
        }
    }

    //Funcion que imprime el vector, se utiliza el bucle para poder asignar el numero de digitos decimales y
    //controlar el espacio entre cada numero, haciendo que la matriz y el vector sean mas faciles de leer
    fun Show() {
        print("[\t")
        for (i in this.indices) {
            print(String.format("%.7f", this[i]) + "\t")
        }
        println("]")
    }
}