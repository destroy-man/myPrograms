package ru.korobeynikov.chapter5

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Отложенные операции и над коллекциями : последовательности
@Composable
fun Chapter5_3() {
    val people = listOf(Person("Alice", 31), Person("Bob", 29), Person("Carol", 31))
    Text(text = " ${people.asSequence().map(Person::name).filter { it.startsWith("A") }.toList()}")
}