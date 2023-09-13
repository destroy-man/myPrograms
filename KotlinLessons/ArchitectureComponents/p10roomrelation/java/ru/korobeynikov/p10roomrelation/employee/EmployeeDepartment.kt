package ru.korobeynikov.p10roomrelation.employee

import androidx.room.ColumnInfo

data class EmployeeDepartment(
    val name: String,
    val salary: Int,
    @ColumnInfo(name = "department_name")
    val departmentName: String
)
