package ru.korobeynikov.p10roomrelation.other

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.korobeynikov.p10roomrelation.department.Department
import ru.korobeynikov.p10roomrelation.department.DepartmentDao
import ru.korobeynikov.p10roomrelation.employee.Employee
import ru.korobeynikov.p10roomrelation.employee.EmployeeDao

@Database(entities = [Employee::class, Department::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    abstract fun departmentDao(): DepartmentDao
}