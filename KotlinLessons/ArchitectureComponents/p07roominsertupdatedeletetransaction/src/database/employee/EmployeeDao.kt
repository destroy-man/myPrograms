package ru.korobeynikov.p07roominsertupdatedeletetransaction.database.employee

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employee")
    fun getAll(): List<Employee>

    @Query("SELECT * FROM employee WHERE id = :id")
    fun getById(id: Long): Employee?

    //Вариации Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employee: Employee)

    @Insert
    fun insertVararg(vararg employees: Employee)

    @Insert
    fun insertList(employees: List<Employee>)

    @Insert
    fun insertIterable(employees: Iterable<Employee>)

    @Insert
    fun insertReturnId(employee: Employee): Long

    @Insert
    fun insertReturnArrayIds(employees: List<Employee>): Array<Long>

    @Insert
    fun insertReturnListIds(employees: List<Employee>): List<Long>

    //Вариации Update
    @Update
    fun update(employee: Employee)

    @Update
    fun updateReturnCountUpdatedRows(employees: List<Employee>): Int

    //Вариации Delete
    @Delete
    fun delete(employee: Employee)

    @Delete
    fun deleteReturnCountDeletedRows(employees: List<Employee>): Int
}