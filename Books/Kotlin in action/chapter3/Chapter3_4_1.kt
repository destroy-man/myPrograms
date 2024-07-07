package ru.korobeynikov.chapter3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Работа с коллекциями : переменное число арrументов, инфиксная форма записи вызова и поддержка в библиотеке
//Расширение API коллекций Java
@Composable
fun Chapter3_4_1() {
    Column {
        val strings: List<String> = listOf("first", "second", "fourteenth")
        Text(text = " ${strings.last()}")
        val numbers: Collection<Int> = setOf(1, 14, 2)
        Text(text = " ${numbers.max()}")
    }
}