package ru.korobeynikov.p13roomtesting.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p13roomtesting.R
import ru.korobeynikov.p13roomtesting.databinding.ActivityMainBinding
import ru.korobeynikov.p13roomtesting.employee.Employee

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val db = App.getInstance().getDatabase()
        val employeeDao = db.employeeDao()
        Thread {
            employeeDao.insert(Employee(name = "John Smith", salary = 10000))
            employeeDao.insert(Employee(name = "Ivan Ivanov", salary = 20000))
            for (employee in employeeDao.getAll())
                log("$employee")
        }.start()
    }

    private fun log(text: String) = Log.d("myLogs", text)
}