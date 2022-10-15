package ru.korobeynikov.p06roomentity

import android.graphics.Bitmap
import androidx.room.*

@Entity
data class Employee(
    val name: String,
    val salary: Int,
    @Ignore
    val avatar: Bitmap?,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
) {
    constructor(name: String, salary: Int, id: Long) : this(name, salary, null, id)
}