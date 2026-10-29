package ru.korobeynikov.p13roomtesting

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
import ru.korobeynikov.p13roomtesting.database.Employee

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = App.instance.getDatabase()
        val employeeDao = db.employeeDao()
        val employees = mutableStateListOf<Employee>()

        lifecycleScope.launch(Dispatchers.IO) {
            db.clearAllTables()

            employeeDao.insert(Employee(1, "John Smith", 10000, 1))
            employeeDao.insert(Employee(2, "Ivan Ivanov", 20000, 2))
            employeeDao.insert(Employee(3, "Petr Petrov", 30000, 3))

            employees.addAll(employeeDao.getAll())
        }

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("All employees:")
                employees.forEach { employee ->
                    Text(employee.toString())
                }
            }
        }
    }
}