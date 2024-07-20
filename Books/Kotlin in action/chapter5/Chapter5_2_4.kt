package ru.korobeynikov.chapter5

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Функциональный API для работы с коллекциями
//Обработка элементов вложенных коллекций: функции flatMap и flatten
@Composable
fun Chapter5_2_4() {
    val strings = listOf("abc", "def")
    val books = listOf(
        Book("Thursday Next", listOf("Jasper Fforde")),
        Book("Mort", listOf("Terry Pratchett")),
        Book("Good Omens", listOf("Terry Pratchett", "Neil Gaiman"))
    )
    Column {
        Text(text = " ${strings.flatMap { it.toList() }}")
        Text(text = " ${books.flatMap { it.authors }.toSet()}")
    }
}