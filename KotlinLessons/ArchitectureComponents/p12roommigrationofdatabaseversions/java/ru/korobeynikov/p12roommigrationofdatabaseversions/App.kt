package ru.korobeynikov.p12roommigrationofdatabaseversions

import android.app.Application
import androidx.room.Room

class App : Application() {

    companion object {

        private lateinit var instance: App

        fun getInstance(): App {
            return instance
        }
    }

    private lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .addMigrations(AppDatabase.MIGRATION_1_2).build()
    }

    fun getDatabase(): AppDatabase {
        return database
    }
}