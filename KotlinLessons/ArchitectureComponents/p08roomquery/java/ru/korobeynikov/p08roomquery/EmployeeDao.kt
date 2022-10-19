package ru.korobeynikov.p08roomquery

import androidx.room.*

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee")
    fun getAll(): List<Employee>

    @Query("SELECT * FROM employee WHERE id IN (:idList)")
    fun getByIdList(idList: List<Long>): List<Employee>

    @Query("DELETE from employee WHERE id IN (:idList)")
    fun deleteByIdList(idList: List<Long>): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(employee: Employee)

    @Update
    fun update(employee: List<Employee>): Int

    @Delete
    fun delete(employee: List<Employee>): Int
}