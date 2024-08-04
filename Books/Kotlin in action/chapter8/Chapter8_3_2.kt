package ru.korobeynikov.chapter8

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Порядок выполнения функций высшего порядка
//Возврат из лямбда-выражений: возврат с помощью меток
@Composable
fun Chapter8_3_2() {
    val people = listOf(Person8_2_3("Alice", 29), Person8_2_3("Bob", 31))
    Text(text = lookForAlice8_3_2(people))
}

fun lookForAlice8_3_2(people: List<Person8_2_3>): String {
    people.forEach {
        if (it.name == "Alice")
            return@forEach
    }
    return " Alice might be somewhere"
}