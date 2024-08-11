package ru.korobeynikov.chapter9

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Вариантность: обобщенные типы и подтипы
//Зачем нужна вариантность: передача арrумента в функцию
@Composable
fun Chapter9_3_1() {
    Text(text = printContents(listOf("abc", "bac")))
}

fun printContents(list: List<Any>) = " ${list.joinToString()}"

fun addAnswer(list: MutableList<Any>) {
    list.add(42)
}