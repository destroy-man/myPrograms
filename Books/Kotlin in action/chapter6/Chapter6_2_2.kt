package ru.korobeynikov.chapter6

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter6.chapter6_2.Person6_2_2

//Примитивные и другие базовые типы
//Примитивные типы с поддержкой null: Int?, Boolean? и прочие
@Composable
fun Chapter6_2_2() {
    Column {
        Text(text = " ${Person6_2_2("Sam", 35).isOlderThan(Person6_2_2("Amy", 42))}")
        Text(text = " ${Person6_2_2("Sam", 35).isOlderThan(Person6_2_2("Jane"))}")
    }
}