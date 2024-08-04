package ru.korobeynikov.chapter7

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Перегрузка арифметических операторов
//Переrрузка составных операторов присваивания
@Composable
fun Chapter7_1_2() {
    val list = arrayListOf(1, 2)
    list += 3
    val newList = list + listOf(4, 5)
    Column {
        Text(text = " $list")
        Text(text = " $newList")
    }
}