package ru.korobeynikov.p10roomrelation.department

import androidx.room.Relation
import ru.korobeynikov.p10roomrelation.employee.Employee
import ru.korobeynikov.p10roomrelation.employee.EmployeeNameAndSalary

data class DepartmentWithEmployees(
    val id: Int,
    val name: String,
    @Relation(parentColumn = "id", entityColumn = "department_id", entity = Employee::class)
    val employees: List<EmployeeNameAndSalary>
)
