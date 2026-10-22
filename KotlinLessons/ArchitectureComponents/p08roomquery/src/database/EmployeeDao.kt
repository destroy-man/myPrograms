package ru.korobeynikov.p08roomquery.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EmployeeDao {

    //Вариации Query
    @Query("SELECT * FROM employee")
    fun getAll(): List<Employee>

    @Query("SELECT * FROM employee")
    fun getAllArray(): Array<Employee>

    @Query("SELECT * FROM employee WHERE id = :id")
    fun getById(id: Long): Employee?

    @Query("SELECT * FROM employee WHERE salary > :minSalary")
    fun getAllWithSalaryMoreThan(minSalary: Int): List<Employee>

    @Query("SELECT * FROM employee WHERE salary BETWEEN :minSalary AND :maxSalary")
    fun getAllWithSalaryBetween(minSalary: Int, maxSalary: Int): List<Employee>

    @Query("SELECT * FROM employee WHERE first_name LIKE :search OR last_name LIKE :search")
    fun getAllWithNameLike(search: String): List<Employee>

    @Query("SELECT * FROM employee WHERE id IN (:idList)")
    fun getByIdList(idList: List<Long>): List<Employee>

    @Query("SELECT first_name, last_name FROM employee")
    fun getNames(): List<Name>

    @Query("UPDATE employee SET salary = :newSalary WHERE id IN (:idList)")
    fun updateSalaryByIdList(idList: List<Long>, newSalary: Int): Int

    @Query("DELETE from employee WHERE id IN (:idList)")
    fun deleteByIdList(idList: List<Long>): Int
    //

    @Insert
    fun insert(employee: Employee)

    @Update
    fun update(employee: Employee)

    @Delete
    fun delete(employee: Employee)
}