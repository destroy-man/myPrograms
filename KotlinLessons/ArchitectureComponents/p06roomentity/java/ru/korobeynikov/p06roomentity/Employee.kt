package ru.korobeynikov.p06roomentity

import android.graphics.Bitmap
import androidx.room.*

@Entity
data class Employee(
    @PrimaryKey
    val id: Long,
    val name: String,
    val salary: Int,
) {
    @Ignore
    val avatar: Bitmap? = null
}