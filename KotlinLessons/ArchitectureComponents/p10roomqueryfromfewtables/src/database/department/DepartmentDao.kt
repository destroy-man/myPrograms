package ru.korobeynikov.p10roomqueryfromfewtables.database.department

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DepartmentDao {

    @Query("SELECT * FROM department")
    fun getAll(): List<Department>

    @Query("SELECT * FROM department WHERE id = :id")
    fun getById(id: Long): Department?

    @Insert
    fun insert(department: Department)

    @Update
    fun update(department: Department)

    @Delete
    fun delete(department: Department)
}