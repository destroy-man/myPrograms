package ru.korobeynikov.chapter7

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.time.LocalDate

//Соглашения для коллекций и диапазонов
//Соглашение "iterator" для цикла "for"
@Composable
fun Chapter7_3_4() {
    val newYear = LocalDate.ofYearDay(2017, 1)
    val daysOff = newYear.minusDays(1)..newYear
    Column {
        for (dayOff in daysOff)
            Text(text = " $dayOff")
    }
}

operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> =
    object : Iterator<LocalDate> {

        var current = start

        override fun hasNext() = current <= endInclusive

        override fun next() = current.apply {
            current = plusDays(1)
        }

    }