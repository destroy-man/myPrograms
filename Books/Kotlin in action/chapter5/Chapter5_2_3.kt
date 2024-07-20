package ru.korobeynikov.chapter5

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Функциональный API для работы с коллекциями
//Группировка значений в списке с функцией groupBy
@Composable
fun Chapter5_2_3() {
    val people = listOf(Person("Alice", 31), Person("Bob", 29), Person("Carol", 31))
    val list = listOf("a", "ab", "b")
    Column {
        Text(text = " ${people.groupBy { it.age }}")
        Text(text = " ${list.groupBy(String::first)}")
    }
}