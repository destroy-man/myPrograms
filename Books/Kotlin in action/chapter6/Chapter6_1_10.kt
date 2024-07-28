package ru.korobeynikov.chapter6

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Поддержка значения null
//Типовые параметры с поддержкой null
@Composable
fun Chapter6_1_10() {
    Text(text = printHashCode(42))
}

fun <T : Any> printHashCode(t: T) = " ${t.hashCode()}"