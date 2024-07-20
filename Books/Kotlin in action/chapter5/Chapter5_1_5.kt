package ru.korobeynikov.chapter5

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Лямбда-выражения и ссылки на члены класса
//Ссылки на члены класса
@Composable
fun Chapter5_1_5() {
    Column {
        val createPerson = ::Person
        val p = createPerson("Alice", 29)
        Text(text = " $p")

        val dmitry = Person("Dmitry", 34)
        val personsAgeFunction = Person::age
        Text(text = " ${personsAgeFunction(dmitry)}")
        val dmitrysAgeFunction = dmitry::age
        Text(text = " ${dmitrysAgeFunction()}")
    }
}