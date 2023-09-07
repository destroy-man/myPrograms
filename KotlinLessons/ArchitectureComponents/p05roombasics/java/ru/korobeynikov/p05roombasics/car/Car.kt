package ru.korobeynikov.p05roombasics.car

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Car(
    @PrimaryKey
    val id: Long,
    val model: String,
    val year: Int
)
