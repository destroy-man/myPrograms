package ru.korobeynikov.chapter3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Добавпение методов в сторонние классы : функции-расширения и свойства-расширения
//Свойства-расширения
@Composable
fun Chapter3_3_5() {
    Column {
        Text(text = " ${StringBuilder("Kotlin").lastChar3_3_5}")
        val sb = StringBuilder("Kotlin?")
        sb.lastChar3_3_5 = '!'
        Text(text = " $sb")
    }
}

var StringBuilder.lastChar3_3_5: Char
    get() = get(length - 1)
    set(value) {
        this.setCharAt(length - 1, value)
    }