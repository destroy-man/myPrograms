package ru.korobeynikov.chapter6

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Поддержка значения null
//Типы с поддержкой значения null
@Composable
fun Chapter6_1_1() {
    val x: String? = null
    Column {
        Text(text = " ${strLenSafe(x)}")
        Text(text = " ${strLenSafe("abc")}")
    }
}

fun strLenSafe(s: String?) =
    if (s != null) s.length
    else 0