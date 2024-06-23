package ru.korobeynikov.chapter2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Основные элементы : переменные и функции
//Функции
@Composable
fun Chapter2_1_2(){
    Text(text = " ${max(1, 2)}")
}

fun max(a: Int, b: Int) = if (a > b) a else b