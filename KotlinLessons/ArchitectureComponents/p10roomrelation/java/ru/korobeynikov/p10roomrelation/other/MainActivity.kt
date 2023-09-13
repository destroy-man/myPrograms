package ru.korobeynikov.p10roomrelation.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p10roomrelation.R
import ru.korobeynikov.p10roomrelation.databinding.ActivityMainBinding
import ru.korobeynikov.p10roomrelation.department.Department
import ru.korobeynikov.p10roomrelation.employee.Employee

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val db = App.getInstance().getDatabase()
        val employeeDao = db.employeeDao()
        val departmentDao = db.departmentDao()
        Thread {
            departmentDao.insert(Department(1, "Marketing"))
            departmentDao.insert(Department(2, "IT"))
            employeeDao.insert(Employee(1, "John Smith", 10000, 1))
            employeeDao.insert(Employee(2, "Ivan Ivanov", 20000, 2))
            employeeDao.insert(Employee(3, "Petr Petrov", 30000, 0))
            for (department in departmentDao.getDepartmentsWithEmployees())
                log("$department")
        }.start()
    }

    private fun log(text: String) = Log.d("myLogs", text)
}