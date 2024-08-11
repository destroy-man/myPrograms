package ru.korobeynikov.chapter9

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Параметры обобщенных типов
//Ограничения типовых параметров
@Composable
fun Chapter9_1_3() {
    val helloWorld = StringBuilder(" Hello World")
    ensureTrailingPeriod(helloWorld)
    Text(text = helloWorld.toString())
}

fun <T : Number> oneHalf(value: T): Double {
    return value.toDouble() / 2.0
}

fun <T : Comparable<T>> max(first: T, second: T): T {
    return if (first > second) first else second
}

fun <T> ensureTrailingPeriod(seq: T) where T : CharSequence, T : Appendable {
    if (!seq.endsWith('.'))
        seq.append('.')
}