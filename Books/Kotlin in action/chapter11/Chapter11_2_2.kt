package ru.korobeynikov.chapter11

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Создание структурированных API : лямбда-выражения с попучателями в DSL
//Использование лямбда-выражений с получателями в построителях разметки HTML
@Composable
fun Chapter11_2_2() {
    Text(text = " ${createAnotherTable()}")
}

fun table(init: TABLE.() -> Unit) = TABLE().apply(init)

fun createTable() =
    table {
        tr {
            td {}
        }
    }

fun createAnotherTable() = table {
    for (i in 1..2) {
        tr {
            td {}
        }
    }
}