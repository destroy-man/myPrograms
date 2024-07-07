package ru.korobeynikov.chapter3

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Добавпение методов в сторонние классы : функции-расширения и свойства-расширения
//Вспомоrательные функции как расширения
@Composable
fun Chapter3_3_3() {
    val list = listOf(1, 2, 3)
    Text(text = " ${list.joinToString3_3_3(separator = "; ", prefix = "(", postfix = ")")}")
}

fun <T> Collection<T>.joinToString3_3_3(
    separator: String = ", ",
    prefix: String = "",
    postfix: String = "",
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0)
            result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}