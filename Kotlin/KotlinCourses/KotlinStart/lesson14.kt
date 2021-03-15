//Напишите функцию, которой в качестве аргумента передается массив, и из которой возвращается словарь, в котором индексы массива становятся ключами, а элементы массива – значениями.
fun main(){
    val a=arrayOf(11,21,32,41,52)
    val m=arrayToMap(a)
    println(m)
}

fun arrayToMap(a:Array<Int>):Map<Int,Int>{
    val m=mutableMapOf(0 to 1)
    for(i in a.indices)
        m[i]=a[i]
    return m
}