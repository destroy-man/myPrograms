package ru.korobeynikov.p10roomrelation.department

import androidx.room.*

@Dao
interface DepartmentDao {

    @Transaction
    @Query("SELECT id, name FROM department")
    fun getDepartmentsWithEmployees(): List<DepartmentWithEmployees>

    @Query("SELECT * FROM department")
    fun getAll(): List<Department>

    @Query("SELECT * FROM department WHERE id = :id")
    fun getById(id: Long): Department

    @Insert
    fun insert(department: Department)

    @Update
    fun update(department: Department)

    @Delete
    fun delete(department: Department)
}