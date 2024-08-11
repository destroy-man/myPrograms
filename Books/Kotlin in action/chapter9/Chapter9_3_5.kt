package ru.korobeynikov.chapter9

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Вариантность: обобщенные типы и подтипы
//Определение вариантности в месте использования: определение вариантности дпя вхождений типов
@Composable
fun Chapter9_3_5() {
    val ints = mutableListOf(1, 2, 3)
    val anyItems = mutableListOf<Any>()
    copyData(ints, anyItems)
    Text(text = " $anyItems")
}

fun <T> copyData(source: MutableList<T>, destination: MutableList<in T>) {
    for (item in source)
        destination.add(item)
}