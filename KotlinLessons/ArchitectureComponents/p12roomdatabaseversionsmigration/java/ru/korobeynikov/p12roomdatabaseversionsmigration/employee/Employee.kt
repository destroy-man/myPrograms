package ru.korobeynikov.p12roomdatabaseversionsmigration.employee

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey
    val id: Long,
    val name: String,
    val salary: Int,
    val birthday: Long
)