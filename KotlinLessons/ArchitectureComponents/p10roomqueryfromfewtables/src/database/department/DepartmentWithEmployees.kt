package ru.korobeynikov.p10roomqueryfromfewtables.database.department

import androidx.room.Embedded
import androidx.room.Relation
import ru.korobeynikov.p10roomqueryfromfewtables.database.employee.Employee
import ru.korobeynikov.p10roomqueryfromfewtables.database.employee.EmployeeNameAndSalary

data class DepartmentWithEmployees(
    @Embedded
    val department: Department,

    //Отобразить все данные по Employee
    //@Relation(parentColumn = "id", entityColumn = "department_id")
    //val employees: List<Employee>

    //Отобразить только name и salary
    @Relation(parentColumn = "id", entityColumn = "department_id", entity = Employee::class)
    val employees: List<EmployeeNameAndSalary>
)
