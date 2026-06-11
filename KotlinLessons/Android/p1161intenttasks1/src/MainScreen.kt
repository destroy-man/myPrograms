package ru.korobeynikov.p1161intenttasks1

import android.app.ActivityManager
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun MainScreen(title: String, onClick: () -> Unit) {
    val logTag = "myLogs"
    val context = LocalContext.current
    val am = context.getSystemService("activity") as ActivityManager
    Column {
        Text(title)
        Button(onClick = onClick) {
            Text(stringResource(R.string.start))
        }
        Button(onClick = {
            val tasks = am.appTasks
            tasks.forEach { task ->
                val baseActivity = task.taskInfo.baseActivity
                if (baseActivity != null && baseActivity.flattenToString()
                        .startsWith("ru.korobeynikov.p116")
                ) {
                    Log.d(logTag, "------------------")
                    Log.d(logTag, "Count: ${task.taskInfo.numActivities}")
                    Log.d(logTag, "Root: ${baseActivity.flattenToShortString()}")
                    Log.d(logTag, "Top: ${task.taskInfo.topActivity?.flattenToShortString()}")
                }
            }
        }) {
            Text(stringResource(R.string.info))
        }
    }
}