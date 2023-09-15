package ru.korobeynikov.p13roomtesting.employee

import androidx.room.*

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee")
    fun getAll(): List<Employee>

    @Query("SELECT * FROM employee WHERE id = :id")
    fun getById(id: Long): Employee

    @Query("SELECT * FROM employee ORDER BY salary DESC")
    fun getAllOrderBySalary(): List<Employee>

    @Insert
    fun insert(employee: Employee)

    @Insert
    fun insertAll(employees: List<Employee>)

    @Update
    fun update(employee: Employee)

    @Delete
    fun delete(employee: Employee)

    @Query("DELETE FROM employee")
    fun deleteAll()
}