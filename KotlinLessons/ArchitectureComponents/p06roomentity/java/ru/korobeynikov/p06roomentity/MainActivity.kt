package ru.korobeynikov.p06roomentity

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
        Thread {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.box)
            val employee = Employee("Ivan Ivanov", 10000, bitmap)
            employeeDao.insert(employee)
            for (element in employeeDao.getAll())
                Log.d(logTag, "$element")
        }.start()
    }
}