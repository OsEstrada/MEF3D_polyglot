package tools

class Matrix() : ArrayList<Vector>(){

    constructor(numRows: Int, numCols: Int, defaultElement: Float) : this() {
        for (i in 0 until numRows) {
            this.add(Vector(numCols, defaultElement))
        }
    }

    //Funcion que imprime la matriz
    fun Show() {
        for (i in this.indices) {
            this.get(i).Show()
        }
    }
}