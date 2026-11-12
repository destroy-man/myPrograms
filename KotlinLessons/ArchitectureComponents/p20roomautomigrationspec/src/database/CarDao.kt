package ru.korobeynikov.p20roomautomigrationspec.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CarDao {

    @Query("SELECT * FROM car")
    fun getAll(): List<Car>

    @Query("SELECT * FROM car WHERE id = :id")
    fun getById(id: Long): Car?

    @Insert
    fun insert(car: Car)

    @Update
    fun update(car: Car)

    @Delete
    fun delete(car: Car)
}