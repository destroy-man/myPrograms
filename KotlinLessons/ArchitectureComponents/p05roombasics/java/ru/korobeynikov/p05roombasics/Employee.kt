package ru.korobeynikov.p05roombasics

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey
    val id: Long,
    val name: String,
    val salary: Int
)