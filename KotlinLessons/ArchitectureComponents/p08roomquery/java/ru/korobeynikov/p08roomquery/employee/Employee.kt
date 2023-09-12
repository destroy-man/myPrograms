package ru.korobeynikov.p08roomquery.employee

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    val salary: Int
)
