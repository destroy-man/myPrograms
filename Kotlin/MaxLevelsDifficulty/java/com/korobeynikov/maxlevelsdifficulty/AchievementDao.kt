package com.korobeynikov.maxlevelsdifficulty

import androidx.room.*

//Интерфейс для взаимодейтсвия с базой данных
@Dao
interface AchievementDao {

    @Query("SELECT * FROM  achievement ORDER BY percent asc,id desc")
    fun getSortedAll():MutableList<Achievement>

    @Query("SELECT * FROM  achievement ORDER BY id asc")
    fun getAll():MutableList<Achievement>

    @Query("SELECT * FROM  achievement WHERE idGame=:id")
    fun getAchievementById(id:Long):Achievement

    @Query("SELECT * FROM  achievement WHERE nameGame=:name")
    fun getAchievementByName(name:String):Achievement

    @Insert
    fun insert(achievement:Achievement)

    @Update
    fun update(achievement:Achievement)

    @Delete
    fun delete(achievement:Achievement)
}