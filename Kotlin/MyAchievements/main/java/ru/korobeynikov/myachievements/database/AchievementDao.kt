package ru.korobeynikov.myachievements.database

import androidx.room.*
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

//Интерфейс для взаимодействия с базой данных
@Dao
interface AchievementDao {

    @Query("SELECT * FROM achievement ORDER BY percent asc,dateTime desc")
    fun getSortedAll(): Flowable<List<Achievement>>

    @Query("SELECT * FROM achievement WHERE idGame=:id")
    fun getAchievementById(id: Long): Maybe<Achievement>

    @Query("SELECT * FROM  achievement WHERE nameGame LIKE :name ORDER BY percent asc,dateTime desc,id desc")
    fun getAchievementsByName(name: String): Flowable<List<Achievement>>

    @Insert
    fun insert(achievement: Achievement)

    @Update
    fun update(achievement: Achievement)

    @Delete
    fun delete(achievement: Achievement)
}