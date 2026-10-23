package ru.korobeynikov.p10roomqueryfromfewtables.database.employee

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val salary: Int,
    @ColumnInfo(name = "department_id")
    val departmentId: Int
)