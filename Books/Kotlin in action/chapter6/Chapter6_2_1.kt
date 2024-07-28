package ru.korobeynikov.chapter6

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Примитивные и другие базовые типы
//Примитивные типы : Int, Boolean и другие
@Composable
fun Chapter6_2_1() {
    Text(text = showProgress(146))
}

fun showProgress(progress: Int): String {
    val percent = progress.coerceIn(0, 100)
    return " We're $percent% done!"
}