package ru.korobeynikov.p06roomentity.car

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CarDao {

    @Query("SELECT * FROM car")
    fun getAll(): List<Car>

    @Insert
    fun insert(car: Car)

    @Delete
    fun delete(car: Car)
}