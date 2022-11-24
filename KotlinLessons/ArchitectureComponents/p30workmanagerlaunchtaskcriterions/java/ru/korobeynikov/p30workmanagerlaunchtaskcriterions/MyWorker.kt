package ru.korobeynikov.p30workmanagerlaunchtaskcriterions

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        const val TAG = "workmng"
    }

    override fun doWork(): Result {
        Log.d(TAG, "doWork: start")
        try {
            for (i in 0 until 10) {
                TimeUnit.SECONDS.sleep(1)
                Log.d(TAG, "$i, isStopped $isStopped")
                if (isStopped)
                    return Result.failure()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(TAG, "doWork: end")
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.d(TAG, "onStopped")
    }
}