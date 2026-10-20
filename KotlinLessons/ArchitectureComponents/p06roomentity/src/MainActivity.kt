package ru.korobeynikov.p06roomentity

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
import ru.korobeynikov.p06roomentity.database.employee.Address
import ru.korobeynikov.p06roomentity.database.car.Car
import ru.korobeynikov.p06roomentity.database.employee.Employee

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = App.instance.getDatabase()
        val employeeDao = db.employeeDao()
        val employees = mutableStateListOf<Employee>()
        val carDao = db.carDao()
        val cars = mutableStateListOf<Car>()

        lifecycleScope.launch(Dispatchers.IO) {
            val address = Address("London", "Baker Street", 221)
            val employee =
                Employee(firstName = "John", lastName = "Smith", salary = 10000, address = address)
            employeeDao.insert(employee)

            employees.addAll(employeeDao.getAll())

            val car = Car(model = "Ferrari", year = 2026, employeeId = 1)
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