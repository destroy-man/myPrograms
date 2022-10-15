package ru.korobeynikov.p06roomentity

import androidx.room.*

@Dao
interface ItemDao {

    @Query("SELECT * FROM item")
    fun getAll(): List<Item>

    @Insert
    fun insert(item: Item)

    @Update
    fun update(item: Item)

    @Delete
    fun delete(item: Item)
}