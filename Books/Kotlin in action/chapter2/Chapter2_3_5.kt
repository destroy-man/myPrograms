package ru.korobeynikov.chapter2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Представление и обработка выбора : перечисления и конструкция "when"
//Автоматическое приведение типов : совмещение проверки и приведения типа
@Composable
fun Chapter2_3_5(){
    Text(text = " ${eval2_3_5(Sum(Sum(Num(1), Num(2)), Num(4)))}")
}

fun eval2_3_5(e: Expr): Int {
    if (e is Num) {
        val n = e as Num
        return n.value
    }
    if (e is Sum) {
        return eval2_3_5(e.right) + eval2_3_5(e.left)
    }
    throw IllegalArgumentException("Unknown expression")
}