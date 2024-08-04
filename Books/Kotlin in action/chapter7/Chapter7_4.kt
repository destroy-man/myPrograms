package ru.korobeynikov.chapter7

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.chapter7.chapter7_4.NameComponents

//Мультидекларации и функции component
@Composable
fun Chapter7_4() {
    val (name, ext) = splitFilename("example.kt")
    Column {
        Text(text = " $name")
        Text(text = " $ext")
    }
}

fun splitFilename(fullName: String): NameComponents {
    val result = fullName.split('.', limit = 2)
    return NameComponents(result[0], result[1])
}