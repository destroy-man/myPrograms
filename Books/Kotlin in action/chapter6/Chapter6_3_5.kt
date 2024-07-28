package ru.korobeynikov.chapter6

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Массивы и коллекции
//Массивы объектов и примитивных типов
@Composable
fun Chapter6_3_5() {
    val array = arrayOf("a", "b", "c")
    Text(text = printArgs(array))
}

fun printArgs(args: Array<String>): String {
    val result = StringBuilder()
    args.forEachIndexed { index, element ->
        result.append(" Argument $index is $element\n")
    }
    return result.toString()
}