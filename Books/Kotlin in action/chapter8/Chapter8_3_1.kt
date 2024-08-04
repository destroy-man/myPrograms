package ru.korobeynikov.chapter8

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Порядок выполнения функций высшего порядка
//Инструкции "return" в лямбда-выражениях: выход из вмещающей функции
@Composable
fun Chapter8_3_1() {
    val people = listOf(Person8_2_3("Alice", 29), Person8_2_3("Bob", 31))
    Text(text = lookForAlice8_3_1(people))
}

fun lookForAlice8_3_1(people: List<Person8_2_3>): String {
    people.forEach {
        if (it.name == "Alice")
            return " Found!"
    }
    return " Alice is not found"
}