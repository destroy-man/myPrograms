package ru.korobeynikov.p31workmanagersequenceexecutetasks

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MyWorker5(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d(MainActivity.TAG, "MyWorker5 start")
        try {
            TimeUnit.SECONDS.sleep(5)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(MainActivity.TAG, "MyWorker5 end")
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.d(MainActivity.TAG, "onStopped")
    }
}