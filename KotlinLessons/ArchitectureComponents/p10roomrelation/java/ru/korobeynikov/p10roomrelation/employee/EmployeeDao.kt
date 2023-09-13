package ru.korobeynikov.p10roomrelation.employee

import androidx.room.*

@Dao
interface EmployeeDao {

    @Query("SELECT employee.name, employee.salary, department.name AS department_name " +
            "FROM employee, department WHERE department.id == employee.department_id")
    fun getEmployeeWithDepartment(): List<EmployeeDepartment>

    @Query("SELECT * FROM employee")
    fun getAll(): List<Employee>

    @Query("SELECT * FROM employee WHERE id = :id")
    fun getById(id: Long): Employee

    @Insert
    fun insert(employee: Employee)

    @Update
    fun update(employee: Employee)

    @Delete
    fun delete(employee: Employee)
}