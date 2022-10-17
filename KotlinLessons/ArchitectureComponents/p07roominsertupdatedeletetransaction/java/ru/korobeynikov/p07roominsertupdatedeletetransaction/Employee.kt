package ru.korobeynikov.p07roominsertupdatedeletetransaction

import android.graphics.Bitmap
import androidx.room.*

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val salary: Int,
    @Ignore
    val avatar: Bitmap?
) {
    constructor(id: Long, name: String, salary: Int) : this(id, name, salary, null)
}
