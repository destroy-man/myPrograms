package ru.korobeynikov.p08roomquery.other

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.korobeynikov.p08roomquery.employee.Employee
import ru.korobeynikov.p08roomquery.employee.EmployeeDao

@Database(entities = [Employee::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}