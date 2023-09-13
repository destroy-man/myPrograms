package ru.korobeynikov.p10roomrelation.employee

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey
    val id: Long = 0,
    val name: String,
    val salary: Int,
    @ColumnInfo(name = "department_id")
    val departmentId: Int
)