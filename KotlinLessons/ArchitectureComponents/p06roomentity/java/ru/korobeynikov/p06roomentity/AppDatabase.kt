package ru.korobeynikov.p06roomentity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Employee::class, Item::class, Car::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun itemDao(): ItemDao
    abstract fun carDao(): CarDao
}