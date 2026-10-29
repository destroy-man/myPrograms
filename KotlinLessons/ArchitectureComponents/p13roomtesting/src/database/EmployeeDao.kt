package ru.korobeynikov.p13roomtesting.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee")
    fun getAll(): List<Employee>

    @Query("SELECT * FROM employee ORDER BY salary DESC")
    fun getAllOrderBySalary(): List<Employee>

    @Insert
    fun insert(employee: Employee)

    @Insert
    fun insertAll(employees: List<Employee>)

    @Update
    fun update(employee: Employee): Int

    @Delete
    fun delete(employee: Employee)

    @Query("DELETE FROM employee")
    fun deleteAll()
}