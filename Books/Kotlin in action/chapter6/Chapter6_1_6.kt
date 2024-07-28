package ru.korobeynikov.chapter6

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Поддержка значения null
//Проверка на null: утверждение "!!"
@Composable
fun Chapter6_1_6() {
    Text(text = " ${ignoreNulls(null)}")
}

fun ignoreNulls(s: String?): String {
    val sNotNull = s!!
    return sNotNull.length.toString()
}