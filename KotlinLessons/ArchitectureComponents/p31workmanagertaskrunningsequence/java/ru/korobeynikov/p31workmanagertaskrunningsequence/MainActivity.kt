package ru.korobeynikov.p31workmanagertaskrunningsequence

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import ru.korobeynikov.p31workmanagertaskrunningsequence.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "myLogs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val myWorkRequest1 = OneTimeWorkRequest.Builder(MyWorker1::class.java).build()
        val myWorkRequest2 = OneTimeWorkRequest.Builder(MyWorker2::class.java).build()
        val myWorkRequest3 = OneTimeWorkRequest.Builder(MyWorker3::class.java).build()
        val myWorkRequest4 = OneTimeWorkRequest.Builder(MyWorker4::class.java).build()
        val myWorkRequest5 = OneTimeWorkRequest.Builder(MyWorker5::class.java).build()
        WorkManager.getInstance(this)
            .beginUniqueWork("work123", ExistingWorkPolicy.APPEND, myWorkRequest1)
            .then(myWorkRequest3).then(myWorkRequest5).enqueue()
        Thread {
            TimeUnit.SECONDS.sleep(5)
            WorkManager.getInstance(this)
                .beginUniqueWork("work123", ExistingWorkPolicy.REPLACE,
                    myWorkRequest1).then(myWorkRequest3).then(myWorkRequest5).enqueue()
        }.start()
    }
}