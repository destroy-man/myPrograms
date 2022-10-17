package ru.korobeynikov.p07roominsertupdatedeletetransaction

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = App.getInstance().getDatabase()
        val employeeDao = db.employeeDao()
        val carDao = db.carDao()
        val employeeCarDao = db.employeeCarDao()
        Thread {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.box)
            val employee = Employee(1, "Ivan Ivanov", 10000, bitmap)
            val car = Car("Ferrari", 2022, 1)
            employeeCarDao.insertCarAndEmployee(car, employee)
            for (element in employeeDao.getAll())
                Log.d(logTag, "$element")
            for (element in carDao.getAll())
                Log.d(logTag, "$element")
        }.start()
    }
}