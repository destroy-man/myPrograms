package ru.korobeynikov.p11roomtypeconverter.employee

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneId

class LocalDateTimeConverter {
    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?) =
        date?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
}