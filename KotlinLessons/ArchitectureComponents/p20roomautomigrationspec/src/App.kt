package ru.korobeynikov.p20roomautomigrationspec

import android.app.Application
import androidx.room.Room
import ru.korobeynikov.p20roomautomigrationspec.database.AppDatabase

class App : Application() {

    private lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .build()
    }

    fun getDatabase(): AppDatabase {
        return database
    }

    companion object {
        lateinit var instance: App
    }
}