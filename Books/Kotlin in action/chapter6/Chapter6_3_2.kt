package ru.korobeynikov.chapter6

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Массивы и коллекции
//Изменяемые и неизменяемые коллекции
@Composable
fun Chapter6_3_2() {
    val source: Collection<Int> = arrayListOf(3, 5, 7)
    val target: MutableCollection<Int> = arrayListOf(1)
    copyElements(source, target)
    Text(text = " $target")
}

fun <T> copyElements(source: Collection<T>, target: MutableCollection<T>) {
    for (item in source)
        target.add(item)
}