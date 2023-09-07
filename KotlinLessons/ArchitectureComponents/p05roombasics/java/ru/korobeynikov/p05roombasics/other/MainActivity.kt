package ru.korobeynikov.p05roombasics.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p05roombasics.R
import ru.korobeynikov.p05roombasics.car.Car
import ru.korobeynikov.p05roombasics.databinding.ActivityMainBinding
import ru.korobeynikov.p05roombasics.employee.Employee

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val db = App.getInstance().getDatabase()
        val employeeDao = db.employeeDao()
        val carDao = db.carDao()
        Thread {
            employeeDao.insert(Employee(1, "John Smith", 10000))
            carDao.insert(Car(1, "Ferrari", 2023))
            Log.d("myLogs", "employees count: ${employeeDao.getAll().size}")
            Log.d("myLogs", "cars count: ${carDao.getAll().size}")
        }.start()
    }
}