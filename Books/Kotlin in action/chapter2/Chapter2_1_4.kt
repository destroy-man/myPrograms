package ru.korobeynikov.chapter2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Основные элементы : переменные и функции
//Простое форматирование строк: щабпоны
@Composable
fun Chapter2_1_4(){
    HelloWorld(args = arrayOf("Bob"))
}

@Composable
fun HelloWorld(args: Array<String>) {
    Text(text = " Hello, ${if (args.isNotEmpty()) args[0] else "someone"}!")
}