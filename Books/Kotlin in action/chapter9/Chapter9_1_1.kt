package ru.korobeynikov.chapter9

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Параметры обобщенных типов
//Обобщенные функции и свойства
@Composable
fun Chapter9_1_1() {
    Text(text = " ${listOf(1, 2, 3, 4).penultimate}")
}

val <T> List<T>.penultimate: T
    get() = this[size - 2]