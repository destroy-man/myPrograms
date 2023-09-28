package ru.korobeynikov.p31workmanagertaskrunningsequence

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MyWorker3(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.d(MainActivity.TAG, "MyWorker3 start")
        try {
            TimeUnit.SECONDS.sleep(3)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(MainActivity.TAG, "MyWorker3 end")
        return Result.success()
    }
}