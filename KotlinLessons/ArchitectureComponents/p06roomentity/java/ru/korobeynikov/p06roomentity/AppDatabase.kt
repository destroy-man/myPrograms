package ru.korobeynikov.p06roomentity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Employee::class, Car::class, Item::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}