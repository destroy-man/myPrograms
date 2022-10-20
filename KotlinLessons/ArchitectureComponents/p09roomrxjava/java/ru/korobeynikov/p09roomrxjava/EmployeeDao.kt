package ru.korobeynikov.p09roomrxjava

import androidx.room.*
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee")
    fun getAll(): Flowable<Employee>

    @Query("SELECT * FROM employee WHERE id = :id")
    fun getById(id: Long): Maybe<Employee>

    @Query("DELETE from employee WHERE id IN (:idList)")
    fun deleteByIdList(idList: List<Long>): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(employee: Employee)

    @Update
    fun update(employee: List<Employee>): Int

    @Delete
    fun delete(employee: List<Employee>): Int
}