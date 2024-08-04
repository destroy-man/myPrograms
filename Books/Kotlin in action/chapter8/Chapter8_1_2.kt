package ru.korobeynikov.chapter8

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Объявление функций высшего порядка
//Вызов функций, переданных в арrументах
@Composable
fun Chapter8_1_2() {
    Column {
        Text(text = twoAndThree { a, b -> a + b })
        Text(text = twoAndThree { a, b -> a * b })
    }
}

fun twoAndThree(operation: (Int, Int) -> Int): String {
    val result = operation(2, 3)
    return " The result is $result"
}

fun String.filter(predicate: (Char) -> Boolean): String {
    val sb = StringBuilder()
    for (index in indices) {
        val element = get(index)
        if (predicate(element))
            sb.append(element)
    }
    return sb.toString()
}