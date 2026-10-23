package ru.korobeynikov.p10roomqueryfromfewtables

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
import ru.korobeynikov.p10roomqueryfromfewtables.database.department.Department
import ru.korobeynikov.p10roomqueryfromfewtables.database.department.DepartmentWithEmployees
import ru.korobeynikov.p10roomqueryfromfewtables.database.employee.Employee

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = App.instance.getDatabase()
        val employeeDao = db.employeeDao()
        val departmentDao = db.departmentDao()
        //val employees = mutableStateListOf<EmployeeDepartment>()
        val departments = mutableStateListOf<DepartmentWithEmployees>()

        lifecycleScope.launch(Dispatchers.IO) {
            db.clearAllTables()

            employeeDao.insert(Employee(1, "John Smith", 10000, 1))
            employeeDao.insert(Employee(2, "Ivan Ivanov", 20000, 2))
            employeeDao.insert(Employee(3, "Petr Petrov", 30000, 2))
            departmentDao.insert(Department(1, "Marketing"))
            departmentDao.insert(Department(2, "IT"))

            //employees.addAll(employeeDao.getEmployeeWithDepartment())
            departments.addAll(employeeDao.getDepartmentsWithEmployees())
        }

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("All departments with employees:")
                departments.forEach { employee ->
                    Text(employee.toString())
                }
            }
        }
    }
}