package ru.korobeynikov.chapter6

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Примитивные и другие базовые типы
//Числовые преобразования
@Composable
fun Chapter6_2_3() {
    val x = 1
    Text(text = " ${x.toLong() in listOf(1L, 2L, 3L)}")
}

fun foo(l: Long) = " $l"