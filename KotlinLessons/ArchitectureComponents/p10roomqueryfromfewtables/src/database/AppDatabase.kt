package ru.korobeynikov.p10roomqueryfromfewtables.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.korobeynikov.p10roomqueryfromfewtables.database.department.Department
import ru.korobeynikov.p10roomqueryfromfewtables.database.department.DepartmentDao
import ru.korobeynikov.p10roomqueryfromfewtables.database.employee.Employee
import ru.korobeynikov.p10roomqueryfromfewtables.database.employee.EmployeeDao

@Database(entities = [Employee::class, Department::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    abstract fun departmentDao(): DepartmentDao
}