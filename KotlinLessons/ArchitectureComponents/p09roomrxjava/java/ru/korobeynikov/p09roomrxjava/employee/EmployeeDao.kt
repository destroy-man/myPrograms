package ru.korobeynikov.p09roomrxjava.employee

import androidx.room.*
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee")
    fun getAll(): Flowable<List<Employee>>

    @Query("SELECT * FROM employee WHERE id = :id")
    fun getById(id: Long): Maybe<Employee>

    @Insert
    fun insert(employee: Employee)

    @Update
    fun update(employee: Employee)

    @Delete
    fun delete(employee: Employee)
}