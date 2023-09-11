package ru.korobeynikov.p07roominsertupdatedeletetransaction.other

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.korobeynikov.p07roominsertupdatedeletetransaction.car.Car
import ru.korobeynikov.p07roominsertupdatedeletetransaction.car.CarDao
import ru.korobeynikov.p07roominsertupdatedeletetransaction.employee.Employee
import ru.korobeynikov.p07roominsertupdatedeletetransaction.employee.EmployeeDao

@Database(entities = [Employee::class, Car::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    abstract fun carDao(): CarDao

    abstract fun employeeCarDao(): EmployeeCarDao
}