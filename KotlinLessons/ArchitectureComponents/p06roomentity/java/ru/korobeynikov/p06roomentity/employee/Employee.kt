package ru.korobeynikov.p06roomentity.employee

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val salary: Int,
) {
    @Ignore
    lateinit var avatar: Bitmap
}
