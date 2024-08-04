package ru.korobeynikov.chapter8

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Объявление функций высшего порядка
//Устранение повторяющихся фраrментов с помощью лямбда-выражений
@Composable
fun Chapter8_1_6() {
    val log = listOf(
        SiteVisit("/", 34.0, OS.WINDOWS),
        SiteVisit("/", 22.0, OS.MAC),
        SiteVisit("/login", 12.0, OS.WINDOWS),
        SiteVisit("/signup", 8.0, OS.IOS),
        SiteVisit("/", 16.3, OS.ANDROID)
    )
    Column {
        Text(
            text = " ${
                log.averageDurationFor {
                    it.os in setOf(OS.ANDROID, OS.IOS)
                }
            }"
        )
        Text(
            text = " ${
                log.averageDurationFor {
                    it.os == OS.IOS && it.path == "/signup"
                }
            }"
        )
    }
}

fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) =
    filter(predicate).map(SiteVisit::duration).average()