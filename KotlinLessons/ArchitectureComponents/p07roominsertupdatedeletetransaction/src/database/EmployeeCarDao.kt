package ru.korobeynikov.p07roominsertupdatedeletetransaction.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import ru.korobeynikov.p07roominsertupdatedeletetransaction.database.employee.Employee
import ru.korobeynikov.p07roominsertupdatedeletetransaction.database.car.Car

@Dao
interface EmployeeCarDao {

    @Insert
    fun insertEmployee(employee: Employee)

    @Insert
    fun insertCar(car: Car)

    @Transaction
    fun insertCarAndEmployee(employee: Employee, car: Car) {
        insertEmployee(employee)
        insertCar(car)
    }
}