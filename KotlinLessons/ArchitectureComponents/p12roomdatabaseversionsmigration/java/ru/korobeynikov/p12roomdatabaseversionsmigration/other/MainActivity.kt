package ru.korobeynikov.p12roomdatabaseversionsmigration.other

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p12roomdatabaseversionsmigration.R
import ru.korobeynikov.p12roomdatabaseversionsmigration.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val db = App.getInstance().getDatabase()
        Thread {
            for (employee in db.employeeDao().getAll())
                log("$employee")
        }.start()
    }

    private fun log(text: String) = Log.d("myLogs", text)
}