package ru.korobeynikov.p07roominsertupdatedeletetransaction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.korobeynikov.p07roominsertupdatedeletetransaction.database.car.Car
import ru.korobeynikov.p07roominsertupdatedeletetransaction.database.employee.Employee

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        var countUpdated = 0
        var countDeleted = 0
         */

        val db = App.instance.getDatabase()
        val employees = mutableStateListOf<Employee>()
        val cars = mutableStateListOf<Car>()
        val employeeDao = db.employeeDao()
        val carDao = db.carDao()
        val employeeCarDao = db.employeeCarDao()

        lifecycleScope.launch(Dispatchers.IO) {
            db.clearAllTables()
            /*
            val employee1 = Employee(1, name = "John Smith", salary = 10000)
            val employee2 = Employee(2, name = "Ivan Ivanov", salary = 20000)
            employeeDao.insertVararg(employee1, employee2)
            employeeDao.insertList(listOf(employee1, employee2))
            employeeDao.insertIterable(listOf(employee1, employee2))
            val id = employeeDao.insertReturnId(employee1)
            val ids = employeeDao.insertReturnArrayIds(listOf(employee1, employee2))
            val ids = employeeDao.insertReturnListIds(listOf(employee1, employee2))

            countUpdated = employeeDao.updateReturnCountUpdatedRows(
                listOf(
                    employee1.copy(salary = 30000),
                    employee2.copy(salary = 40000)
                )
            )

            countDeleted = employeeDao.deleteReturnCountDeletedRows(listOf(employee2))
             */

            val employee = Employee(1, name = "John Smith", salary = 10000)
            val car = Car(1, "Ferrari", 2026)
            employeeCarDao.insertCarAndEmployee(employee, car)
            employees.addAll(employeeDao.getAll())
            cars.addAll(carDao.getAll())
        }

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("All employees:")
                employees.forEach { employee ->
                    Text(employee.toString())
                }
                Text("All cars:")
                cars.forEach { car ->
                    Text(car.toString())
                }
            }
        }
    }
}