package ru.korobeynikov.p05roombasics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = App.getInstance().getDatabase()
        val employeeDao = db.employeeDao()
        val carDao = db.carDao()
        Thread {
            Log.d("myLogs", "employees: ${employeeDao.getAll().size}")
            Log.d("myLogs", "cars: ${carDao.getAll().size}")
        }.start()
    }
}