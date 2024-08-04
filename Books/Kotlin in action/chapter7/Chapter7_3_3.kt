package ru.korobeynikov.chapter7

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.time.LocalDate

//Соглашения для коллекций и диапазонов
//Соглашение rangeTo
@Composable
fun Chapter7_3_3() {
    val now = LocalDate.now()
    val vacation = now..now.plusDays(10)
    Text(text = " ${now.plusWeeks(1) in vacation}")
}