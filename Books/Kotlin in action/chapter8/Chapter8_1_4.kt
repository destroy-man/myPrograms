package ru.korobeynikov.chapter8

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Объявление функций высшего порядка
//Значения по умолчанию и пустые значения для параметров типов функций
@Composable
fun Chapter8_1_4() {
    val letters = listOf("Alpha", "Beta")
    Column {
        Text(text = " ${letters.joinToString()}")
        Text(
            text = " ${
                letters.joinToString {
                    it.lowercase()
                }
            }"
        )
        Text(
            text = " ${
                letters.joinToString(separator = "! ", postfix = "! ", transform = {
                    it.uppercase()
                })
            }"
        )
    }
}

fun <T> Collection<T>.joinToString(
    separator: String = ", ",
    prefix: String = "",
    postfix: String = "",
    transform: ((T) -> String)? = null,
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0)
            result.append(separator)
        val str = transform?.invoke(element) ?: element.toString()
        result.append(str)
    }
    result.append(postfix)
    return result.toString()
}