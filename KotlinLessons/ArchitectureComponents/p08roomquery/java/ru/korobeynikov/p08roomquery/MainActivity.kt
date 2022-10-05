package ru.korobeynikov.p08roomquery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = App.getInstance().getDatabase()
        val employeesLiveData = db.employeeDao().getAll()
        employeesLiveData.observe(this) { employees ->
            Log.d("myLogs", "onChanged $employees")
        }
        Thread {
            db.employeeDao().deleteByIdList(listOf(1, 3, 4))
        }.start()
    }
}