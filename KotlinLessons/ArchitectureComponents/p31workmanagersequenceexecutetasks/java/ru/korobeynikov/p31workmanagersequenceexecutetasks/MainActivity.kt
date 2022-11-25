package ru.korobeynikov.p31workmanagersequenceexecutetasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "workmng"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myWorkRequest1 = OneTimeWorkRequest.Builder(MyWorker1::class.java).build()
        val myWorkRequest3 = OneTimeWorkRequest.Builder(MyWorker3::class.java).build()
        val myWorkRequest5 = OneTimeWorkRequest.Builder(MyWorker5::class.java).build()
        WorkManager.getInstance()
            .beginUniqueWork("work123", ExistingWorkPolicy.APPEND, myWorkRequest1)
            .then(myWorkRequest3).then(myWorkRequest5).enqueue().state.observe(this) { state ->
                Log.d(TAG, "enqueue $state")
            }
        Thread {
            TimeUnit.SECONDS.sleep(5)
            WorkManager.getInstance()
                .beginUniqueWork("work123", ExistingWorkPolicy.REPLACE, myWorkRequest1)
                .then(myWorkRequest3).then(myWorkRequest5).enqueue()
        }.start()
    }
}