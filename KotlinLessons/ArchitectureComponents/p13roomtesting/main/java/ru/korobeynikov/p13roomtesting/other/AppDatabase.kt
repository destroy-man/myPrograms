package ru.korobeynikov.p13roomtesting.other

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.korobeynikov.p13roomtesting.employee.Employee
import ru.korobeynikov.p13roomtesting.employee.EmployeeDao

@Database(entities = [Employee::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}