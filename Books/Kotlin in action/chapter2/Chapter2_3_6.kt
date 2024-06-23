package ru.korobeynikov.chapter2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Представление и обработка выбора : перечисления и конструкция "when"
//Рефакторинг: замена "if" на "when"
@Composable
fun Chapter2_3_6(){
    Text(text = " ${eval2_3_6(Sum(Num(1), Num(2)))}")
}

fun eval2_3_6(e: Expr): Int =
    when (e) {
        is Num -> e.value
        is Sum -> eval2_3_6(e.right) + eval2_3_6(e.left)
        else -> throw IllegalArgumentException("Unknown expression")
    }