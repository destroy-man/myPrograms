package ru.korobeynikov.p10roomrelation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey
    val id: Long,
    val name: String,
    val salary: Int,
    @ColumnInfo(name = "department_id")
    val departmentId: Int
)
