package ru.korobeynikov.chapter5

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Отложенные операции и над коллекциями : последовательности
//Создание последовательностей
@Composable
fun Chapter5_3_2() {
    val naturalNumbers = generateSequence(0) { it + 1 }
    val numbersTo100 = naturalNumbers.takeWhile { it <= 100 }
    Text(text = " ${numbersTo100.sum()}")
}