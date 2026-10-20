package ru.korobeynikov.p06roomentity.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.korobeynikov.p06roomentity.database.car.Car
import ru.korobeynikov.p06roomentity.database.car.CarDao
import ru.korobeynikov.p06roomentity.database.employee.Employee
import ru.korobeynikov.p06roomentity.database.employee.EmployeeDao

@Database(entities = [Employee::class, Car::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    abstract fun carDao(): CarDao
}