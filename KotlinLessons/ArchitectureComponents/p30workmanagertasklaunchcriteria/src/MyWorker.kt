package ru.korobeynikov.p30workmanagertasklaunchcriteria

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d(Constants.TAG, "doWork: start")

        try {
            for (i in 0 until 10) {
                TimeUnit.SECONDS.sleep(1)
                Log.d(Constants.TAG, "$i, isStopped $isStopped")
                if (isStopped) {
                    return Result.failure()
                }
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        Log.d(Constants.TAG, "doWork: end")

        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.d(Constants.TAG, "onStopped")
    }
}