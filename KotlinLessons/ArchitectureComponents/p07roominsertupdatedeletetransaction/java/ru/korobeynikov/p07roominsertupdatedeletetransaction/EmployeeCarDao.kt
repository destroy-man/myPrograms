package ru.korobeynikov.p07roominsertupdatedeletetransaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction

@Dao
abstract class EmployeeCarDao {

    @Insert
    abstract fun insertEmployee(employee: Employee)

    @Insert
    abstract fun insertCar(car: Car)

    @Transaction
    open fun insertCarAndEmployee(car: Car, employee: Employee) {
        insertEmployee(employee)
        insertCar(car)
    }
}