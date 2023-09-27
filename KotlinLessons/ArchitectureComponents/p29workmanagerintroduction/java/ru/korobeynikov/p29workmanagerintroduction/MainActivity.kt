package ru.korobeynikov.p29workmanagerintroduction

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import ru.korobeynikov.p29workmanagerintroduction.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "myLogs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val myWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 30,
            TimeUnit.MINUTES, 25, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueue(myWorkRequest)
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(myWorkRequest.id)
            .observe(this) { workInfo ->
                Log.d(TAG, "onChanged: ${workInfo.state}")
            }
    }
}