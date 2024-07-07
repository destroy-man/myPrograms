package ru.korobeynikov.chapter3

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Добавпение методов в сторонние классы : функции-расширения и свойства-расширения
@Composable
fun Chapter3_3() {
    Text(text = " ${"Kotlin".lastChar3_3()}")
}

fun String.lastChar3_3(): Char = this[this.length - 1]