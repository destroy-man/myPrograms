package ru.korobeynikov.p08roomquery.other

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p08roomquery.R
import ru.korobeynikov.p08roomquery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val db = App.getInstance().getDatabase()
        val employeeDao = db.employeeDao()
        Thread {
            val deletedCount = employeeDao.deleteByIdList(listOf(1L, 3L, 4L))
            log("deleted count = $deletedCount")
            for (employee in employeeDao.getAll())
                Log.d("myLogs", "$employee")
        }.start()
    }

    private fun log(text: String) = Log.d("myLogs", text)
}