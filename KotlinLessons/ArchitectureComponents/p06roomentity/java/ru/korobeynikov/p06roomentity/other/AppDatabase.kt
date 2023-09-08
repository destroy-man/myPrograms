package ru.korobeynikov.p06roomentity.other

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.korobeynikov.p06roomentity.car.Car
import ru.korobeynikov.p06roomentity.car.CarDao
import ru.korobeynikov.p06roomentity.employee.Employee
import ru.korobeynikov.p06roomentity.employee.EmployeeDao

@Database(entities = [Employee::class, Car::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    abstract fun carDao(): CarDao
}