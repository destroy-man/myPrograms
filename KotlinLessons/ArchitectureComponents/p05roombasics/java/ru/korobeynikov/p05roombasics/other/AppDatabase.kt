package ru.korobeynikov.p05roombasics.other

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.korobeynikov.p05roombasics.car.Car
import ru.korobeynikov.p05roombasics.car.CarDao
import ru.korobeynikov.p05roombasics.employee.Employee
import ru.korobeynikov.p05roombasics.employee.EmployeeDao

@Database(entities = [Employee::class, Car::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    abstract fun carDao(): CarDao
}