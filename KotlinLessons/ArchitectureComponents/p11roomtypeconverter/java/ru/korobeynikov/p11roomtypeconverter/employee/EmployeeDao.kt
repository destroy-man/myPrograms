package ru.korobeynikov.p11roomtypeconverter.employee

import androidx.room.*
import java.time.LocalDateTime

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee WHERE birthday = :birthdayDate")
    fun getByDate(birthdayDate: LocalDateTime): Employee

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