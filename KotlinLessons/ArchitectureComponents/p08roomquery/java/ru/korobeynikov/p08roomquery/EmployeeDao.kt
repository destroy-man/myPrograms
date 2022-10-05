package ru.korobeynikov.p08roomquery

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee")
    fun getAll(): LiveData<List<Employee>>

    @Query("DELETE from employee WHERE id IN (:idList)")
    fun deleteByIdList(idList: List<Long>): Int

    @Insert
    fun insert(employee: Employee)
}