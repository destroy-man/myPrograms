import kotlin.random.Random

fun main(){
    //Заполните список числами. Выведите на экран. Примените к списку функцию-метод shuffle(). Снова выведите список на экран.
    //var list=mutableListOf(1,2,3,5,4)
    //for(i in list)
    //    println(i)
    //list.shuffle()
    //for(i in list)
    //    println(i)
    //Заполните список случайными числами, отсортируйте его с помощью метода sort(), удалите из списка минимальное и максимальное значения.
    var list=mutableListOf(0,0,0,0,0)
    for(i in list.indices)
        list[i]=Random.nextInt(-100,100)
    println("Original list:")
    for(i in list)
        println(i)
    list.sort()
    list.removeAt(0)
    list.removeAt(list.size-1)
    println("Modified list:")
    for(i in list)
        println(i)
}