package ru.korobeynikov.chapter6

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Поддержка значения null
//Расширение типов с поддержкой null
@Composable
fun Chapter6_1_9() {
    Column {
        Text(text = verifyUserInput(" "))
        Text(text = verifyUserInput(null))
    }
}

fun verifyUserInput(input: String?): String {
    return if (input.isNullOrBlank())
        " Please fill in required fields"
    else "Is not blank"
}