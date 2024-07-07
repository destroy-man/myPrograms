package ru.korobeynikov.chapter3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Упрощение вызова функций
//Значения параметров по умолчанию
@Composable
fun Chapter3_2_2() {
    val list = listOf(1, 2, 3)
    Column {
        Text(text = " ${joinToString3_2_2(list, ", ", "", "")}")
        Text(text = " ${joinToString3_2_2(list)}")
        Text(text = " ${joinToString3_2_2(list, "; ")}")
    }
}

fun <T> joinToString3_2_2(
    collection: Collection<T>,
    separator: String = ", ",
    prefix: String = "",
    postfix: String = "",
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