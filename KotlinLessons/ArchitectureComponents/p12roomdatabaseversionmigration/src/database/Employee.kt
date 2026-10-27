package ru.korobeynikov.p12roomdatabaseversionmigration.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val salary: Int,
    //Новое поле V2
    val birthday: Long
)