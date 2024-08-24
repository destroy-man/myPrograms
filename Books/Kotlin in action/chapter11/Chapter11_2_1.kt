package ru.korobeynikov.chapter11

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Создание структурированных API : лямбда-выражения с получатепями в DSL
//Лямбда-выражения с получатепями и типы функций-расширений
@Composable
fun Chapter11_2_1() {
    val s = buildString {
        this.append(" Hello, ")
        append("World!")
    }
    Text(text = s)
}

fun buildString(builderAction: StringBuilder.() -> Unit): String {
    val sb = StringBuilder()
    sb.builderAction()
    return sb.toString()
}

val appendExcl: StringBuilder.() -> Unit = {
    this.append("!")
}