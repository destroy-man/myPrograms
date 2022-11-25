package ru.korobeynikov.p31workmanagersequenceexecutetasks

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MyWorker1(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d(MainActivity.TAG, "MyWorker1 start")
        try {
            TimeUnit.SECONDS.sleep(1)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(MainActivity.TAG, "MyWorker1 end")
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.d(MainActivity.TAG, "onStopped")
    }
}