package ru.korobeynikov.myachievements.database

import androidx.room.Database
import androidx.room.RoomDatabase

//Абстрактный класс представляющий сущность базы данных
@Database(entities = [Achievement::class], version = 1)
abstract class AchievementDatabase : RoomDatabase() {
    abstract fun achievementDao(): AchievementDao
}