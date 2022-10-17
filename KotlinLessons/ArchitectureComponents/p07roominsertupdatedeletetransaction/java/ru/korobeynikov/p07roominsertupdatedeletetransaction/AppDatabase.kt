package ru.korobeynikov.p07roominsertupdatedeletetransaction

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Employee::class, Car::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun carDao(): CarDao
    abstract fun employeeCarDao(): EmployeeCarDao
}