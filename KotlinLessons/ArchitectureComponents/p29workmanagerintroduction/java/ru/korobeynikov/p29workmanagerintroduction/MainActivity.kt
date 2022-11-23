package ru.korobeynikov.p29workmanagerintroduction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        WorkManager.getInstance().enqueue(myWorkRequest)
        WorkManager.getInstance().getWorkInfoByIdLiveData(myWorkRequest.id).observe(this) { workInfo ->
            Log.d(MyWorker.TAG, "${workInfo.state}")
        }
    }
}