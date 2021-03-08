import kotlin.random.Random

fun main(){
    //Заполните массив строками, которые пользователь вводит с клавиатуры и затем выведите их на экран в обратном порядке.
    //var sArr:Array<String> = arrayOf("","","")
    //var i=0
    //while(i<sArr.size) {
    //    sArr[i] = readLine().toString()
    //    i++
    //}
    //i--
    //while(i>=0) {
    //    println(sArr[i])
    //    i--
    //}
    //Заполните массив случайными целыми числами. Найдите элементы с максимальным и минимальным значением.
    val a:Array<Int> = arrayOf(0,0,0,0,0)
    for(i in a)
        a[i]=Random.nextInt(10)
    println(a.max())
    println(a.min())
}