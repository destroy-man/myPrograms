package ru.korobeynikov.chapter4

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter4.chapter4_1.Expr
import ru.korobeynikov.chapter4.chapter4_1.Expr.*

//Создание иерархий классов
//Запечатанные классы : определение жестко заданных иерархии
@Composable
fun Chapter4_1_5() {
    Text(text = " ${eval(Sum(Sum(Num(1), Num(2)), Num(4)))}")
}

fun eval(e: Expr): Int = when (e) {
    is Num -> e.value
    is Sum -> eval(e.right) + eval(e.left)
}