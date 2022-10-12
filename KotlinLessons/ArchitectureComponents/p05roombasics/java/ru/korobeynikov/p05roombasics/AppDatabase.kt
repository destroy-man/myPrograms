package ru.korobeynikov.p05roombasics

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Employee::class, Car::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun carDao(): CarDao
}