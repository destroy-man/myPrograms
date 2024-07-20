package ru.korobeynikov.chapter5

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Отложенные операции и над коллекциями : последовательности
//Выполнение операций над последовательностями: промежуточная и завершающая операции
@Composable
fun Chapter5_3_1() {
    val people = listOf(
        Person("Alice", 29),
        Person("Bob", 31),
        Person("Charles", 31),
        Person("Dan", 21)
    )
    Column {
        Text(text = " ${people.asSequence().map(Person::name).filter { it.length < 4 }.toList()}")
        Text(
            text = " ${
                people.asSequence().filter { it.name.length < 4 }.map(Person::name).toList()
            }"
        )
    }
}