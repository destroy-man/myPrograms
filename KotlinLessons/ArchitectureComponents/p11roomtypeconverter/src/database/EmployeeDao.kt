package ru.korobeynikov.p11roomtypeconverter.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Update
import java.util.Date

@Dao
//@TypeConverters(DateConverter::class)
interface EmployeeDao {

    @Query("SELECT * FROM employee")
    fun getAll(): List<Employee>

    @Query("SELECT * FROM employee WHERE id = :id")
    fun getById(id: Long): Employee?

    @Query("SELECT * FROM employee WHERE birthday = :birthdayDate")
    fun getByDate(@TypeConverters(DateConverter::class) birthdayDate: Date): Employee

    @Query("SELECT * FROM employee WHERE birthday BETWEEN :birthdayFrom AND :birthdayTo")
    @TypeConverters(DateConverter::class)
    fun getByDateBetween(birthdayFrom: Date, birthdayTo: Date): List<Employee>

    @Insert
    fun insert(employee: Employee)

    @Update
    fun update(employee: Employee)

    @Delete
    fun delete(employee: Employee)
}