package ru.korobeynikov.p12roomdatabaseversionmigration

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
import ru.korobeynikov.p12roomdatabaseversionmigration.database.Employee
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = App.instance.getDatabase()
        val employeeDao = db.employeeDao()
        val employees = mutableStateListOf<Employee>()

        lifecycleScope.launch(Dispatchers.IO) {
            db.clearAllTables()

            val calendar = Calendar.getInstance()
            calendar.set(1991, 1, 1)
            val birthday1 = calendar.time
            calendar.set(1992, 2, 2)
            val birthday2 = calendar.time
            calendar.set(1993, 3, 3)
            val birthday3 = calendar.time

            employeeDao.insert(Employee(1, "John Smith", 10000, birthday1.time))
            employeeDao.insert(Employee(2, "Ivan Ivanov", 20000, birthday2.time))
            employeeDao.insert(Employee(3, "Petr Petrov", 30000, birthday3.time))

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