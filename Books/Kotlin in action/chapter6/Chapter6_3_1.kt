package ru.korobeynikov.chapter6

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.io.BufferedReader
import java.io.StringReader

//Массивы и коллекции
//Коллекции и допустимость значения null
@Composable
fun Chapter6_3_1() {
    val reader = BufferedReader(StringReader("1\nabc\n42"))
    val numbers = readNumbers(reader)
    Text(text = addValidNumbers(numbers))
}

fun readNumbers(reader: BufferedReader): List<Int?> {
    val result = ArrayList<Int?>()
    for (line in reader.lineSequence()) {
        try {
            val number = line.toInt()
            result.add(number)
        } catch (e: NumberFormatException) {
            result.add(null)
        }
    }
    return result
}

fun addValidNumbers(numbers: List<Int?>): String {
    val validNumbers = numbers.filterNotNull()
    return " Sum of valid numbers: ${validNumbers.sum()}\n Invalid numbers: ${numbers.size - validNumbers.size}"
}