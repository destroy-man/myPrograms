package ru.korobeynikov.p07roominsertupdatedeletetransaction

import androidx.room.*

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee")
    fun getAll(): List<Employee>

    @Query("SELECT * FROM employee WHERE id = :id")
    fun getById(id: Long): Employee

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(employee: Employee)

    @Update
    fun update(employee: List<Employee>): Int

    @Delete
    fun delete(employee: List<Employee>): Int
}