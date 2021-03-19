package lesson1

fun main(){
    val a= NumInc()
    val b= NumInc()
    a.number=10
    a.step=2
    a.incArg(3)
    b.dec()
    println("a: ${a.number}")
    println("b: ${b.number}")
}