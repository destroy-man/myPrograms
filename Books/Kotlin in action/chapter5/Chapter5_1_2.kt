package ru.korobeynikov.chapter5

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Лямбда-выражения и ссылки на члены класса
//Лямбда-выражения и коллекции
@Composable
fun Chapter5_1_2() {
    val people = listOf(Person("Alice", 29), Person("Bob", 31))
    Text(text = " ${people.maxBy(Person::age)}")
}