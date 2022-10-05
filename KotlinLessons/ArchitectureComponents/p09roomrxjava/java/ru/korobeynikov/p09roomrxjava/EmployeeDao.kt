package ru.korobeynikov.p09roomrxjava

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Maybe

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee WHERE id = :id")
    fun getById(id: Long): Maybe<Employee>

    @Insert
    fun insert(employee: Employee)
}