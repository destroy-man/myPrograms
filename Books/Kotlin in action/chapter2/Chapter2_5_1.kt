package ru.korobeynikov.chapter2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.io.BufferedReader
import java.io.StringReader

//Исключения в KotLin
//"try", "catch" и "finaLLy"
@Composable
fun Chapter2_5_1() {
    val reader = BufferedReader(StringReader("239"))
    Text(text = " ${readNumber2_5_1(reader)}")
}

fun readNumber2_5_1(reader: BufferedReader): Int? {
    try {
        val line = reader.readLine()
        return line.toInt()
    } catch (e: NumberFormatException) {
        return null
    } finally {
        reader.close()
    }
}