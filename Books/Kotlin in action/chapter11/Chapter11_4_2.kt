package ru.korobeynikov.chapter11

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.time.LocalDate
import java.time.Period

//Предметно-ориентированные языки Kotlin на практике
//Определение расширений для простых типов : обработка дат
@Composable
fun Chapter11_4_2() {
    Column {
        Text(text = " ${1.days.ago}")
        Text(text = " ${1.days.fromNow}")
    }
}

val Int.days: Period
    get() = Period.ofDays(this)

val Period.ago: LocalDate
    get() = LocalDate.now() - this

val Period.fromNow: LocalDate
    get() = LocalDate.now() + this