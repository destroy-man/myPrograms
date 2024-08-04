package ru.korobeynikov.chapter8

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Встраиваемые функции : устранение накладных расходов лямбда-выражений
//Встраивание операций с коллекциями
@Composable
fun Chapter8_2_3() {
    val people = listOf(Person8_2_3("Alice", 29), Person8_2_3("Bob", 31))
    Text(text = " ${people.filter { it.age > 30 }.map(Person8_2_3::name)}")
}