package ru.korobeynikov.p08roomquery.employee

import androidx.room.*

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee")
    fun getAll(): List<Employee>

    @Query("SELECT * FROM employee WHERE id = :employeeId")
    fun getById(employeeId: Long): Employee

    @Query("DELETE from employee WHERE id IN (:idList)")
    fun deleteByIdList(idList: List<Long>): Int

    @Insert
    fun insert(employee: Employee)

    @Update
    fun update(employee: Employee)

    @Delete
    fun delete(employee: Employee)
}