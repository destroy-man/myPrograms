package ru.korobeynikov.p09roomrxjava.other

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.korobeynikov.p09roomrxjava.employee.Employee
import ru.korobeynikov.p09roomrxjava.employee.EmployeeDao

@Database(entities = [Employee::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}