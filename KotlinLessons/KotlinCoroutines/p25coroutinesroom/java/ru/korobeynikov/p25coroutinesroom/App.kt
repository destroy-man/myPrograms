package ru.korobeynikov.p25coroutinesroom

import android.app.Application
import androidx.room.Room

class App : Application() {

    companion object {

        private lateinit var instance: App

        fun getInstance() = instance
    }

    private lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database").build()
    }

    fun getDatabase() = database
}