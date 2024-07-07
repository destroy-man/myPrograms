package ru.korobeynikov.chapter3

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Работа со строками и регулярными выражениями
//Разбиение строк
@Composable
fun Chapter3_5_1() {
    val strings = "12.345-6.A".split("[.\\-]".toRegex())
    Text(text = " $strings")
}