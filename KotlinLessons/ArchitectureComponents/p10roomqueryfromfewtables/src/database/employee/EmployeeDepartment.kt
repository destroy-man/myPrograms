package ru.korobeynikov.p10roomqueryfromfewtables.database.employee

import androidx.room.ColumnInfo

data class EmployeeDepartment(
    val name: String,
    val salary: Int,
    @ColumnInfo(name = "department_name")
    val departmentName: String
)
