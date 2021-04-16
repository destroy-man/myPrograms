package com.korobeynikov.maxlevelsdifficulty

import androidx.room.*

@Dao
interface AchievementDao {

    @Query("SELECT * FROM  achievement ORDER BY percent asc,id desc")
    fun getSortedAll():MutableList<Achievement>

    @Query("SELECT * FROM  achievement WHERE idGame=:id")
    fun getAchievement(id:Long):Achievement

    @Insert
    fun insert(achievement:Achievement)

    @Update
    fun update(achievement:Achievement)

    @Delete
    fun delete(achievement:Achievement)
}