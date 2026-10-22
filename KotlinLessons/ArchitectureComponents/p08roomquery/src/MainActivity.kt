package ru.korobeynikov.p08roomquery

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
import ru.korobeynikov.p08roomquery.database.Employee
import ru.korobeynikov.p08roomquery.database.Name

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = App.instance.getDatabase()
        val employeeDao = db.employeeDao()
        val employees = mutableStateListOf<Employee>()
        val names = mutableStateListOf<Name>()
        var updatedCount = 0
        var deletedCount = 0

        lifecycleScope.launch(Dispatchers.IO) {
            db.clearAllTables()

            employeeDao.insert(Employee(1, "John", "Smith", 10000))
            employeeDao.insert(Employee(2, "Ivan", "Ivanov", 20000))
            employeeDao.insert(Employee(3, "Petr", "Petrov", 30000))

            //employees.addAll(employeeDao.getAllArray())

            //val employee = employeeDao.getById(1)
            //if (employee != null)
            //    employees.add(employee)

            //employees.addAll(employeeDao.getAllWithSalaryMoreThan(15000))
            //employees.addAll(employeeDao.getAllWithSalaryBetween(5000, 25000))
            //employees.addAll(employeeDao.getAllWithNameLike("Ivan")) firstName или lastName должны быть равны строке Ivan
            //employees.addAll(employeeDao.getAllWithNameLike("%van%")) firstName или lastName должны содержать подстроку van
            //employees.addAll(employeeDao.getByIdList(listOf(1, 3)))

            updatedCount = employeeDao.updateSalaryByIdList(listOf(1, 3), 40000)

            deletedCount = employeeDao.deleteByIdList(listOf(1, 4))

            employees.addAll(employeeDao.getAll())
            names.addAll(employeeDao.getNames())
        }

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("All employees:")
                employees.forEach { employee ->
                    Text(employee.toString())
                }
                Text("Names of all employees:")
                names.forEach { name ->
                    Text(name.toString())
                }
                Text("Count updated rows = $updatedCount")
                Text("Count deleted rows = $deletedCount")
            }
        }
    }
}