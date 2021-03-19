package lesson4

fun main(){
    val a:List<NumInc> = listOf(NumMult(3),NumMult(3,4,3),NumInc(10,3),NumInc(5,1))
    for(i in a){
        i.inc()
        println(i.number)
    }
}