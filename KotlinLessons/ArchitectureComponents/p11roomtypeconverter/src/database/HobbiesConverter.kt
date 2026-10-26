package ru.korobeynikov.p11roomtypeconverter.database

import androidx.room.TypeConverter

class HobbiesConverter {

    @TypeConverter
    fun fromHobbies(hobbies: List<String>): String {
        return hobbies.joinToString(",")
    }

    @TypeConverter
    fun toHobbies(data: String): List<String> {
        return data.split(",")
    }
}