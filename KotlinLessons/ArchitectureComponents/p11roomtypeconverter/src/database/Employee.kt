package ru.korobeynikov.p11roomtypeconverter.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val salary: Int,
//    @TypeConverters(HobbiesConverter::class)
//    val hobbies: List<String>
    val birthday: Long
)