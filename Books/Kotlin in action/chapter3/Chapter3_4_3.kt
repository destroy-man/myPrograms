package ru.korobeynikov.chapter3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Работа с коллекциями : переменное число арrументов, инфиксная форма записи вызова и поддержка в библиотеке
//Работа с парами: инфиксные вызовы и мупьтидекларации
@Composable
fun Chapter3_4_3() {
    Column {
        val (number, name) = 1 to "one"
        Text(text = " $number = $name")
        val collection = listOf(1, 7, 53)
        for ((index, element) in collection.withIndex())
            Text(text = " $index: $element")
    }
}