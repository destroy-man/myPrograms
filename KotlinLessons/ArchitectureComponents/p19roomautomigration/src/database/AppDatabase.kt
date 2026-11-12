package ru.korobeynikov.p19roomautomigration.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Employee::class], version = 2, autoMigrations = [AutoMigration(1, 2)])
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}