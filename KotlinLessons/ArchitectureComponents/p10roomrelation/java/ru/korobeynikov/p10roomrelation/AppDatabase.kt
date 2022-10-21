package ru.korobeynikov.p10roomrelation

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Employee::class, Department::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun departmentDao(): DepartmentDao
}