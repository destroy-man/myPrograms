package ru.korobeynikov.chapter3

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Работа с коллекциями : переменное число арrументов, инфиксная форма записи вызова и поддержка в библиотеке
//Функции, принимающие произвольное число арrументов
@Composable
fun Chapter3_4_2() {
    val args = arrayOf(1, 2, 3)
    val list = listOf("args: ", *args)
    Text(text = " $list")
}