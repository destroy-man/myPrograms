package ru.korobeynikov.p13roomtesting

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import ru.korobeynikov.p13roomtesting.database.AppDatabase
import ru.korobeynikov.p13roomtesting.database.Employee
import ru.korobeynikov.p13roomtesting.database.EmployeeDao

class EmployeeDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var employeeDao: EmployeeDao

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder<AppDatabase>(
            InstrumentationRegistry.getInstrumentation().context
        ).build()
        employeeDao = db.employeeDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertOneExample() {
        val employees = listOf(Employee(1, "John Smith", 10000))

        employeeDao.insert(employees[0])
        val dbEmployees = employeeDao.getAll()

        assertEquals(1, dbEmployees.size)
        assertTrue(employees[0] == dbEmployees[0])
    }

    @Test
    fun updateExample() {
        val employee = Employee(1, "John Smith", 10000)
        employeeDao.insert(employee)

        employeeDao.update(
            employee.copy(
                salary = employee.salary + 100,
                name = employee.name + " test"
            )
        )

        val dbEmployees = employeeDao.getAll()
        assertTrue(dbEmployees[0].salary == 10100 && dbEmployees[0].name.endsWith(" test"))
    }

    @Test
    fun insertManyExample() {
        val employees = listOf(
            Employee(1, "John Smith", 10000),
            Employee(2, "Ivan Ivanov", 20000),
            Employee(3, "Petr Petrov", 30000)
        )

        employeeDao.insertAll(employees)

        assertEquals(3, employeeDao.getAll().size)
    }

    @Test
    fun deleteAllExample() {
        val employees = listOf(
            Employee(1, "John Smith", 10000),
            Employee(2, "Ivan Ivanov", 20000),
            Employee(3, "Petr Petrov", 30000)
        )
        employeeDao.insertAll(employees)

        employeeDao.deleteAll()

        assertTrue(employeeDao.getAll().isEmpty())
    }

    @Test
    fun orderExample() {
        var employees = listOf(
            Employee(1, "John Smith", 10000),
            Employee(2, "Ivan Ivanov", 20000),
            Employee(3, "Petr Petrov", 30000)
        )
        employeeDao.insertAll(employees)

        employees = employees.sortedWith { employee1, employee2 ->
            employee2.salary - employee1.salary
        }

        assertEquals(employees, employeeDao.getAllOrderBySalary())
    }
}