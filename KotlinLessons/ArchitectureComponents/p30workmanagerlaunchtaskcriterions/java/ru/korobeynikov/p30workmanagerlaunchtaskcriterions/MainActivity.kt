package ru.korobeynikov.p30workmanagerlaunchtaskcriterions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val myWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setConstraints(constraints).build()
        WorkManager.getInstance().enqueue(myWorkRequest)
        WorkManager.getInstance().getWorkInfoByIdLiveData(myWorkRequest.id).observe(this) { workInfo ->
            Log.d(MyWorker.TAG, "onChanged: ${workInfo?.state}")
        }
    }
}