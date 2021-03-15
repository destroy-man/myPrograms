import kotlin.random.Random
fun main(){
    //Напишите программу, которая генерирует случайное число от 32 до 122 включительно. Потом преобразовывает число в символ, а дальше проверяет что это – цифра, буква или все остальное. Выводит соответствующее сообщение.
    val i= Random.nextInt(32,123)
    when{
        i.toChar().isDigit()->println("i is digit")
        i.toChar().isLetter()->println("i is letter")
        else->println("i is other")
    }
}