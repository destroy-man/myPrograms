package ru.korobeynikov.p06roomentity

import androidx.room.Entity

@Entity(primaryKeys = ["key1", "key2"])
data class Item(
    val key1: Long,
    val key2: Long
)
