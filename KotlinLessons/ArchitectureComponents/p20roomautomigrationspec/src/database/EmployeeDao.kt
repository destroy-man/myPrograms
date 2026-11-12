package ru.korobeynikov.p20roomautomigrationspec.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EmployeeDao {

    //V1-2
    //@Query("SELECT * FROM employee")
    @Query("SELECT * FROM staff")
    fun getAll(): List<Employee>

    //V1-2
    //@Query("SELECT * FROM employee WHERE id = :id")
    @Query("SELECT * FROM staff WHERE id = :id")
    fun getById(id: Long): Employee?

    @Insert
    fun insert(employee: Employee)

    @Update
    fun update(employee: Employee)

    @Delete
    fun delete(employee: Employee)
}