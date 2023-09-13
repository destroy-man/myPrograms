package ru.korobeynikov.p10roomrelation.department

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Department(
    @PrimaryKey
    val id: Int,
    val name: String
)
