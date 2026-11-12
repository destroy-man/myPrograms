package ru.korobeynikov.p20roomautomigrationspec.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Car(
    @PrimaryKey
    val id: Long = 0,
    val model: String,
    val year: Int
)
