package ru.korobeynikov.chapter5

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Лямбда-выражения с получателями : функции "with" и "apply"
//Функция "apply"
@Composable
fun Chapter5_5_2() {
    Text(text = " ${alphabet5_5_2()}")
}

fun alphabet5_5_2() = StringBuilder().apply {
    for (letter in 'A'..'Z')
        append(letter)
    append("\nNow I know the alphabet!")
}.toString()