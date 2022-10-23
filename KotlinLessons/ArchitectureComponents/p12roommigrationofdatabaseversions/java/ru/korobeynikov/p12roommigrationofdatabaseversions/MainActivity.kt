package ru.korobeynikov.p12roommigrationofdatabaseversions

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
        Thread {
            for (element in employeeDao.getAll())
                Log.d(logTag, "$element")
        }.start()
    }
}