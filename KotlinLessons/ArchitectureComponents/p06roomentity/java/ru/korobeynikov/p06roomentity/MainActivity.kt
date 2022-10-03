package ru.korobeynikov.p06roomentity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = App.getInstance().getDatabase()
        Thread {
            val employees = db.employeeDao().getAll()
            for (employee in employees)
                Log.d("myLogs", "${employee.id} ${employee.name} ${employee.salary} " +
                        "${employee.avatar}")
        }.start()
    }
}