package ru.korobeynikov.p10roomqueryfromfewtables.database.employee

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ru.korobeynikov.p10roomqueryfromfewtables.database.department.DepartmentWithEmployees

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee")
    fun getAll(): List<Employee>

    @Query("SELECT * FROM employee WHERE id = :id")
    fun getById(id: Long): Employee?

    @Query(
        "SELECT employee.name, employee.salary, department.name AS department_name " +
                "FROM employee, department " +
                "WHERE department.id == employee.department_id"
    )
    fun getEmployeeWithDepartment(): List<EmployeeDepartment>

    @Transaction
    @Query("select id, name from department")
    fun getDepartmentsWithEmployees(): List<DepartmentWithEmployees>

    @Insert
    fun insert(employee: Employee)

    @Update
    fun update(employee: Employee)

    @Delete
    fun delete(employee: Employee)
}