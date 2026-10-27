package ru.korobeynikov.p12roomdatabaseversionmigration.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration

@Database(entities = [Employee::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    companion object {
        val MIGRATION_1_2 = Migration(1, 2) { database ->
            database.execSQL("ALTER TABLE Employee ADD COLUMN birthday INTEGER DEFAULT 0 NOT NULL")
        }
    }
}