package ru.korobeynikov.chapter8

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Порядок выполнения функций высшего порядка
//Анонимные функции : по умолчанию возврат выполняется локально
@Composable
fun Chapter8_3_3() {
    val people = listOf(Person8_2_3("Alice", 29), Person8_2_3("Bob", 31))
    Text(text = " ${people.filter(fun(person) = person.age < 30)}")
}

fun lookForAlice8_3_3(people: List<Person8_2_3>) {
    people.forEach(fun(person) {
        if (person.name == "Alice") return
        Log.d("myLogs", "${person.name} is not Alice")
    })
}