package ru.korobeynikov.p13roomtesting.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration

@Database(entities = [Employee::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    companion object {
        val MIGRATION_1_2 = Migration(1, 2) { database ->
            database.execSQL("ALTER TABLE employee ADD COLUMN taxclass INTEGER DEFAULT 0 NOT NULL")
            database.execSQL("UPDATE employee SET taxclass = 1 WHERE salary < 10000")
            database.execSQL("UPDATE employee SET taxclass = 2 WHERE salary BETWEEN 10000 AND 20000")
            database.execSQL("UPDATE employee SET taxclass = 3 WHERE salary > 20000")
        }
    }
}