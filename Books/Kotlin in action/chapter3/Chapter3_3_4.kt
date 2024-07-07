package ru.korobeynikov.chapter3

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Добавпение методов в сторонние классы : функции-расширения и свойства-расширения
//Функции-расширения не переопределяются
@Composable
fun Chapter3_3_4() {
    val view: View = Button()
    Text(text = " ${view.showOff()}")
}

fun View.showOff() = "I'm a view!"
fun Button.showOff() = "I'm a button!"