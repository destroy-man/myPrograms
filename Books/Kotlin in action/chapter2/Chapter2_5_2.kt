package ru.korobeynikov.chapter2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.io.BufferedReader
import java.io.StringReader

//Исключения в KotLin
//"try" как выражение
@Composable
fun Chapter2_5_2() {
    val reader = BufferedReader(StringReader("not a number"))
    Text(text = " ${readNumber2_5_2(reader)}")
}

fun readNumber2_5_2(reader: BufferedReader) = try {
    reader.readLine().toInt()
} catch (e: NumberFormatException) {
    null
}