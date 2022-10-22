package ru.korobeynikov.p11roomtypeconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = App.getInstance().getDatabase()
        val employeeDao = db.employeeDao()
        Thread {
            val calendarYesterday = Calendar.getInstance()
            calendarYesterday.add(Calendar.DAY_OF_MONTH, -1)
            val calendarTomorrow = Calendar.getInstance()
            calendarTomorrow.add(Calendar.DAY_OF_MONTH, 1)
            val calendarToday = Calendar.getInstance()
            employeeDao.insert(Employee(1, "Ivan Ivanov", 10000, calendarToday.timeInMillis))
            val employee = employeeDao.getByDate(calendarYesterday.time, calendarTomorrow.time)
            Log.d(logTag, "$employee")
        }.start()
    }
}