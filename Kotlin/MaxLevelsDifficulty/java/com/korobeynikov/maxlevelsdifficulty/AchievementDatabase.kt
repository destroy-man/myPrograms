package com.korobeynikov.maxlevelsdifficulty

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities=arrayOf(Achievement::class),version=1)
abstract class AchievementDatabase:RoomDatabase(){
    abstract fun achieventDao():AchievementDao
}