package ru.korobeynikov.chapter5

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Функциональный API для работы с коллекциями
//Применение предикатов к коллекциям: функции "all", "any", "count" и "find"
@Composable
fun Chapter5_2_2() {
    val people = listOf(Person("Alice", 27), Person("Bob", 31))
    Column {
        Text(text = " ${people.all(canBeInClub27)}")
        Text(text = " ${people.any(canBeInClub27)}")
        Text(text = " ${people.count(canBeInClub27)}")
        Text(text = " ${people.find(canBeInClub27)}")
    }
}

val canBeInClub27 = { p: Person -> p.age <= 27 }