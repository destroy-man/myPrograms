package ru.korobeynikov.ch11proxy.withoutproxy

interface TimetableTrains {

    fun getTimetable(info: String): Array<String>

    fun getTrainDepartureTime(trainId: String, info: String): String
}