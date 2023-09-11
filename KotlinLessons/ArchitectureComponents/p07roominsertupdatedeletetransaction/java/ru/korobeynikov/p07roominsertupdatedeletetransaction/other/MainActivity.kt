package ru.korobeynikov.p07roominsertupdatedeletetransaction.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p07roominsertupdatedeletetransaction.R
import ru.korobeynikov.p07roominsertupdatedeletetransaction.car.Car
import ru.korobeynikov.p07roominsertupdatedeletetransaction.databinding.ActivityMainBinding
import ru.korobeynikov.p07roominsertupdatedeletetransaction.employee.Employee

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val db = App.getInstance().getDatabase()
        Thread {
            val employee = Employee(name = "John Smith", salary = 10000)
            val car = Car(model = "Ferrari", year = 2023)
            db.employeeCarDao().insertCarAndEmployee(car, employee)
            for (elementEmployee in db.employeeDao().getAll())
                Log.d(logTag, "$elementEmployee")
            for (elementCar in db.carDao().getAll())
                Log.d(logTag, "$elementCar")
        }.start()
    }
}