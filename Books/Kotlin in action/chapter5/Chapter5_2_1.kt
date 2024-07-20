package ru.korobeynikov.chapter5

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Функциональный API для работы с коллекциями
//Основы: filter и map
@Composable
fun Chapter5_2_1() {
    val people = listOf(Person("Alice", 29), Person("Bob", 31))
    val maxAge = people.maxBy(Person::age).age
    Column {
        Text(text = " ${people.filter { it.age > 30 }.map(Person::name)}")
        Text(text = " ${people.filter { it.age == maxAge }}")
    }
}