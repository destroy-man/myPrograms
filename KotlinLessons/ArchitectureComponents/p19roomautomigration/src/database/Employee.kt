package ru.korobeynikov.p19roomautomigration.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val salary: Int,
    //Новое поле V2
    @ColumnInfo(defaultValue = "0")
    val birthday: Long
)
