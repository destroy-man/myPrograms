package ru.korobeynikov.p10roomrelation

import androidx.room.Relation

data class DepartmentWithEmployees(
    val id: Int,
    val name: String,
    @Relation(parentColumn = "id", entityColumn = "department_id", entity = Employee::class)
    val employees: List<EmployeeNameAndSalary>
)
