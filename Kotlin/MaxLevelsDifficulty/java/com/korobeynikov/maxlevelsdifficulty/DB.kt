package com.korobeynikov.maxlevelsdifficulty

import android.app.Application
import androidx.room.Room

class DB: Application() {

    private lateinit var database:AchievementDatabase

    override fun onCreate() {
        super.onCreate()
        instance=this
        database=Room.databaseBuilder(this,AchievementDatabase::class.java,"database").build()
    }

    companion object {
        lateinit var instance:DB
        @JvmName("getInstance1")
        fun getInstance(): DB {
            return instance
        }
    }

    fun getDatabase():AchievementDatabase{
        return database
    }
}