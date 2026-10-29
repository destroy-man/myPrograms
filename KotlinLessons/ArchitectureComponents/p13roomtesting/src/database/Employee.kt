package ru.korobeynikov.p13roomtesting.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val salary: Int,
    //Новое поле V2
    val taxclass: Int = 0
)