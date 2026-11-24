package ru.korobeynikov.p29workmanagerintroduction

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d(Constants.TAG, "doWork: start")

        try {
            TimeUnit.SECONDS.sleep(10)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        if (!isStopped) {
            Log.d(Constants.TAG, "doWork: end")
        }

        //return Result.retry()
        return Result.success()
    }
}