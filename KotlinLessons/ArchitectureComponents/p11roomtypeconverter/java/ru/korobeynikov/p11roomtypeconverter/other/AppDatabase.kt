package ru.korobeynikov.p11roomtypeconverter.other

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.korobeynikov.p11roomtypeconverter.employee.Employee
import ru.korobeynikov.p11roomtypeconverter.employee.EmployeeDao
import ru.korobeynikov.p11roomtypeconverter.employee.LocalDateTimeConverter

@Database(entities = [Employee::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}