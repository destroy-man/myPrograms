package ru.korobeynikov.p10roomqueryfromfewtables.database.department

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Department(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)
