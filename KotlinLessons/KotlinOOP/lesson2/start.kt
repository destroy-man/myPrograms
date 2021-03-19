package lesson2

fun main(){
    val a=NumInc(10,2)
    val b=NumInc(5)
    val c=NumInc()
    a.inc()
    b.dec()
    c.inc()
    println("a: ${a.number}")
    println("b: ${b.number}")
    println("c: ${c.number}")
}