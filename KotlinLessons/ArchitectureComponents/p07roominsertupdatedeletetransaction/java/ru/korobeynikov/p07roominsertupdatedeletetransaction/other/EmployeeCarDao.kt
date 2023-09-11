package ru.korobeynikov.p07roominsertupdatedeletetransaction.other

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import ru.korobeynikov.p07roominsertupdatedeletetransaction.car.Car
import ru.korobeynikov.p07roominsertupdatedeletetransaction.employee.Employee

@Dao
abstract class EmployeeCarDao {

    @Insert
    abstract fun insertEmployee(employee: Employee)

    @Insert
    abstract fun insertCar(car: Car)

    @Transaction
    open fun insertCarAndEmployee(car: Car, employee: Employee) {
        insertCar(car)
        insertEmployee(employee)
    }
}