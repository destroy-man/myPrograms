package ru.korobeynikov.p12roomdatabaseversionsmigration.other

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.korobeynikov.p12roomdatabaseversionsmigration.employee.Employee
import ru.korobeynikov.p12roomdatabaseversionsmigration.employee.EmployeeDao

@Database(entities = [Employee::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE employee ADD COLUMN birthday INTEGER DEFAULT 0 NOT NULL")
            }
        }
    }

    abstract fun employeeDao(): EmployeeDao
}