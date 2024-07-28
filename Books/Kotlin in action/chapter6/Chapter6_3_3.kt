package ru.korobeynikov.chapter6

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter6.chapter6_3.CollectionUtils

//Массивы и коллекции
//Коллекции Kotlin и язык Java
@Composable
fun Chapter6_3_3() {
    val list = listOf("a", "b", "c")
    Text(text = printInUppercase(list))
}

fun printInUppercase(list: List<String>) =
    " ${CollectionUtils.uppercaseAll(list)}\n ${list.first()}"