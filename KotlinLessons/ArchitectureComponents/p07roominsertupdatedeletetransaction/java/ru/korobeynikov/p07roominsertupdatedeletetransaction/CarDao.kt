package ru.korobeynikov.p07roominsertupdatedeletetransaction

import androidx.room.*

@Dao
interface CarDao {

    @Query("SELECT * FROM car")
    fun getAll(): List<Car>

    @Insert
    fun insert(car: Car)

    @Update
    fun update(car: Car)

    @Delete
    fun delete(car: Car)
}