package ru.korobeynikov.p05roombasics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = App.getInstance().getDatabase()
        val carDao = db.carDao()
        Thread {
            val cars = carDao.getAll()
            for (car in cars)
                carDao.delete(car)
            Log.d(logTag, "${carDao.getAll().size}")
        }.start()
    }
}