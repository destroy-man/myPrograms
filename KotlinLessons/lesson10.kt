import kotlin.random.Random

fun main(){
    //Заполните массив с помощью цикла for случайными числами в диапазоне от -10 до 10. Выведите элементы на экран. С помощью другого цикла for замените отрицательные значения массива на 0. Снова выведите элементы на экран.
    //val a=Array(5,{0})
    //for(i in a.indices) {
    //    a[i] = Random.nextInt(-10, 10)
    //    println(a[i])
    //}
    //for(i in a.indices){
    //    if(a[i]<0)
    //        a[i]=0
    //    println(a[i])
    //}
    //Создайте массив строк. Выведите на экран только те строки, которые содержат определенный символ.
    val s=arrayOf("sad","mad","crazy")
    for(i in s.indices)
        if(s[i].contains('d'))
            println(s[i])
}