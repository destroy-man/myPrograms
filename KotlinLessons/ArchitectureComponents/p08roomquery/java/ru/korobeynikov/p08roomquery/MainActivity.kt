package ru.korobeynikov.p08roomquery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = App.getInstance().getDatabase()
        Thread {
            val deleteCount = db.employeeDao().deleteByIdList(listOf(1, 3, 4))
            Log.d(logTag, "$deleteCount")
        }.start()
    }
}