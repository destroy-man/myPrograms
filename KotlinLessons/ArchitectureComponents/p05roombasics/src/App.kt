package ru.korobeynikov.p05roombasics

import android.app.Application
import androidx.room.Room
import ru.korobeynikov.p05roombasics.database.AppDatabase

class App : Application() {

    private lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            //.allowMainThreadQueries() операции с БД могут выполняться в UI потоке
            .build()
    }

    fun getDatabase(): AppDatabase {
        return database
    }

    companion object {
        lateinit var instance: App
    }
}