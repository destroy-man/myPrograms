package ru.korobeynikov.chapter9

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Обобщенные типы во время выполнения: стирание и овеществление параметров типов
//Обобщенные типы во время выполнения: проверка и приведение типов
@Composable
fun Chapter9_2_1() {
    Text(text = printSum(listOf(1, 2, 3)))
}

fun printSum(c: Collection<*>): String {
    val intList = c as? List<Int> ?: throw IllegalArgumentException("List is expected")
    return " ${intList.sum()}"
}