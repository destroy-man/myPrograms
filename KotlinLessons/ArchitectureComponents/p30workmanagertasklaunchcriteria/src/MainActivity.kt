package ru.korobeynikov.p30workmanagertasklaunchcriteria

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        val workManager = WorkManager.getInstance(this)
        val myWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class)
            .setConstraints(constraints)
            .build()
        workManager.enqueue(myWorkRequest)

        lifecycleScope.launch {
            workManager.getWorkInfoByIdFlow(myWorkRequest.id).collect { workInfo ->
                Log.d(Constants.TAG, "request state: ${workInfo?.state}")
            }
        }

        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                Text("work request = $myWorkRequest")
            }
        }
    }
}