package ru.korobeynikov.p13roomtesting

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.korobeynikov.p13roomtesting.employee.EmployeeDao
import ru.korobeynikov.p13roomtesting.other.AppDatabase

class EmployeeDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var employeeDao: EmployeeDao

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext<Application>(),
            AppDatabase::class.java).build()
        employeeDao = db.employeeDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun checkOrderBySalary() {
        val employees = EmployeeTestHelper.createListOfEmployee(5)
        employeeDao.insertAll(employees)
        employees.sortWith { o1, o2 ->
            o2.salary - o1.salary
        }
        assertEquals(employees, employeeDao.getAllOrderBySalary())
    }
}