package ru.korobeynikov.p30workmanagertasklaunchcriterions

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MyWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        Log.d(MainActivity.TAG, "doWork: start")
        try {
            for (i in 0 until 10) {
                TimeUnit.SECONDS.sleep(1)
                Log.d(MainActivity.TAG, ", isStopped $isStopped")
                if (isStopped)
                    return Result.failure()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(MainActivity.TAG, "doWork: end")
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.d(MainActivity.TAG, "onStopped")
    }
}