package ru.korobeynikov.p29workmanagerintroduction

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
        Log.d(TAG, applicationContext.packageName)
        Log.d(TAG, "doWork: start")
        try {
            TimeUnit.SECONDS.sleep(10)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(TAG, "doWork: end")
        return Result.success()
    }
}