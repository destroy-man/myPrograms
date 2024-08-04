package ru.korobeynikov.chapter7

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Мультидекларации и функции component
//Мультидекларации и циклы
@Composable
fun Chapter7_4_1() {
    val map = mapOf("Oracle" to "Java", "JetBrains" to "Kotlin")
    Text(text = printEntries(map))
}

fun printEntries(map: Map<String, String>): String {
    val entries = StringBuilder()
    for ((key, value) in map)
        entries.append(" $key -> $value\n")
    return entries.toString()
}