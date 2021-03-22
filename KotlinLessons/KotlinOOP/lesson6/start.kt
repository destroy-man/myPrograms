package lesson6

fun main(){
    var a=NumInc(3,2)
    a++
    println("${a.number},${a.step}")
    a--
    println("${a.number},${a.step}")
}