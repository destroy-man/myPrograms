package ru.korobeynikov.p20roomautomigrationspec

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
import ru.korobeynikov.p20roomautomigrationspec.database.Car
import ru.korobeynikov.p20roomautomigrationspec.database.Employee

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = App.instance.getDatabase()
        val employeeDao = db.employeeDao()
        val employees = mutableStateListOf<Employee>()
        //v1
        //val carDao = db.carDao()
        //val cars = mutableStateListOf<Car>()

        lifecycleScope.launch(Dispatchers.IO) {
            db.clearAllTables()

            //v1-3
            //employeeDao.insert(Employee(1, "John Smith", 10000))
            //employeeDao.insert(Employee(2, "Ivan Ivanov", 20000))
            //employeeDao.insert(Employee(3, "Petr Petrov", 30000))

            employeeDao.insert(Employee(1, "John Smith"))
            employeeDao.insert(Employee(2, "Ivan Ivanov"))
            employeeDao.insert(Employee(3, "Petr Petrov"))

            employees.addAll(employeeDao.getAll())

            /*v1
            carDao.insert(Car(1, model = "Ferrari", 2001))
            carDao.insert(Car(2, model = "BMW", 2002))
            carDao.insert(Car(3, model = "Mercedes", 2003))

            cars.addAll(carDao.getAll())
             */
        }

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("All employees:")
                employees.forEach { employee ->
                    Text(employee.toString())
                }
                /*v1
                Text("All cars:")
                cars.forEach { car ->
                    Text(car.toString())
                }
                 */
            }
        }
    }
}