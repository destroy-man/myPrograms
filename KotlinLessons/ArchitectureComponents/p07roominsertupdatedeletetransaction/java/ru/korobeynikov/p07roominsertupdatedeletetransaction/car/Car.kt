package ru.korobeynikov.p07roominsertupdatedeletetransaction.car

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Car(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val model: String,
    val year: Int
)
