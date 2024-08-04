package ru.korobeynikov.chapter8

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Объявление функций высшего порядка
//Типы функций
@Composable
fun Chapter8_1_1() {
    val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
    val action: () -> Int = { 42 }
    Text(text = " ${action.invoke()}")
}