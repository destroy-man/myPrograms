package ru.korobeynikov.ch11proxy.withoutproxy

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TimetableScreen() {
    //Без паттерна Заместитель
    val info = StringBuilder("9B-6854;Лондон;Прага;13:43;21:15;07:32\n")
    info.appendLine("BA-1404;Париж;Грац;14:25;21:25;07:00")
    info.appendLine("9B-8710;Прага;Вена;04:48;08:49;04:01")
    info.append("9B-8122;Прага;Грац;04:48;08:49;04:01")
    val timetableTrains = TimetableElectricTrains()
    val timetables = timetableTrains.getTimetable(info.toString())
    Text("Поезд   Откуда   Куда   Время отправления   Время прибытия   Время в пути")
    timetables.forEach { timetable ->
        val timetableArr = timetable.split(";")
        Text(
            "${timetableArr[0]}   ${timetableArr[1]}   ${timetableArr[2]}   " +
                    "${timetableArr[3]}   ${timetableArr[4]}   ${timetableArr[5]}\t"
        )
    }
}