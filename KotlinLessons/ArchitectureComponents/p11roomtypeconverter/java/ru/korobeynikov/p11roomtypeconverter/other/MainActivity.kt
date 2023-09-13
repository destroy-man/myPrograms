package ru.korobeynikov.p11roomtypeconverter.other

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p11roomtypeconverter.R
import ru.korobeynikov.p11roomtypeconverter.databinding.ActivityMainBinding
import ru.korobeynikov.p11roomtypeconverter.employee.Employee
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val db = App.getInstance().getDatabase()
        val employeeDao = db.employeeDao()
        Thread {
            val time = LocalTime.of(0, 0)
            val date1 = LocalDate.of(1991, 1, 1)
            val datetime1 =
                LocalDateTime.of(date1, time).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val date2 = LocalDate.of(1992, 2, 2)
            val datetime2 =
                LocalDateTime.of(date2, time).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            employeeDao.insert(Employee(1, "John Smith", 10000, datetime1))
            employeeDao.insert(Employee(2, "Ivan Ivanov", 20000, datetime2))
            log("${employeeDao.getByDate(LocalDateTime.of(date1, time))}")
        }.start()
    }

    private fun log(text: String) = Log.d("myLogs", text)
}