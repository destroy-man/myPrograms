package ru.korobeynikov.p09roomrxjava.employee

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val salary: Int
)
