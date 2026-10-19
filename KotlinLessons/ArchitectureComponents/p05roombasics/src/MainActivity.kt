package ru.korobeynikov.p05roombasics

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
import ru.korobeynikov.p05roombasics.database.Car
import ru.korobeynikov.p05roombasics.database.Employee

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = App.instance.getDatabase()
        val employeeDao = db.employeeDao()
        val employees = mutableStateListOf<Employee>()
        val carDao = db.carDao()
        val cars = mutableStateListOf<Car>()

        lifecycleScope.launch(Dispatchers.IO) {
            var employee = employeeDao.getById(1)
            if (employee != null)
                employeeDao.delete(employee)

            employee = Employee(1, "John Smith", 10000)
            employeeDao.insert(employee)

            employeeDao.update(employee.copy(salary = 20000))

            employees.addAll(employeeDao.getAll())

            val car = Car(1, "Ferrari", 2026)
            carDao.insert(car)

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