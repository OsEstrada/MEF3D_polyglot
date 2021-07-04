package tools

fun showKs(Ks: ArrayList<Matrix>) {
    for (i in Ks.indices) {
        println("K del elemento" + (i + 1))
        Ks[i].Show()
        println("**********************************")
    }
}

//Muestra ls bs
fun showbs(bs: ArrayList<Vector>) {
    for (i in bs.indices) {
        println("b del elemento" + (i + 1))
        bs[i].Show()
        println("**********************************")
    }
}