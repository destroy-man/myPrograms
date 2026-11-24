package ru.korobeynikov.p29workmanagerintroduction

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workManager = WorkManager.getInstance(this)
        val myWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class)
            //.addTag("mytag")
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()
//        val myWorkRequest = PeriodicWorkRequest.Builder(
//            MyWorker::class,
//            30,
//            TimeUnit.MINUTES,
//            25,
//            TimeUnit.MINUTES
//        ).build()
        workManager.enqueue(myWorkRequest)

        lifecycleScope.launch {
            workManager.getWorkInfoByIdFlow(myWorkRequest.id).collect { workInfo ->
                Log.d(Constants.TAG, "request state: ${workInfo?.state}")
            }
        }

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("work request = $myWorkRequest")
                Button(onClick = {
                    //workManager.cancelAllWorkByTag("mytag")
                    workManager.cancelWorkById(myWorkRequest.id)
                }) {
                    Text("Отменить задачу")
                }
            }
        }
    }
}