package ru.korobeynikov.p06roomentity.other

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p06roomentity.R
import ru.korobeynikov.p06roomentity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val db = App.getInstance().getDatabase()
        val employeeDao = db.employeeDao()
        Thread {
            for (employee in employeeDao.getAll())
                Log.d("myLogs", "$employee")
        }.start()
    }
}