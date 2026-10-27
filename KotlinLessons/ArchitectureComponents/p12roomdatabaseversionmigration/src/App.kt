package ru.korobeynikov.p12roomdatabaseversionmigration

import android.app.Application
import androidx.room.Room
import ru.korobeynikov.p12roomdatabaseversionmigration.database.AppDatabase

class App : Application() {

    private lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            //.fallbackToDestructiveMigration(false) пересоздает бд с уничтожением всех данных
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .build()
    }

    fun getDatabase(): AppDatabase {
        return database
    }

    companion object {
        lateinit var instance: App
    }
}