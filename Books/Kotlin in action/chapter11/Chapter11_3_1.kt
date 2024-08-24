package ru.korobeynikov.chapter11

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Гибкое вложение блоков с использованием соглашения "invoke"
//Соглашение "invoke" : объекты, вызываемые как функции
@Composable
fun Chapter11_3_1() {
    val bavarianGreeter = Greeter("Servus")
    Text(text = bavarianGreeter.invoke("Dmitry"))
}