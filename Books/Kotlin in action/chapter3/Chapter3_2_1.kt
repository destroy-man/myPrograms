package ru.korobeynikov.chapter3

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Упрощение вызова функций
//Именованные аргументы
@Composable
fun Chapter3_2_1() {
    val collection = listOf(1, 2, 3)
    Text(text = " ${joinToString3_2_1(collection, separator = " ", prefix = " ", postfix = ".")}")
}

fun <T> joinToString3_2_1(
    collection: Collection<T>,
    separator: String,
    prefix: String,
    postfix: String,
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in collection.withIndex()) {
        if (index > 0)
            result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}