package ru.korobeynikov.p31workmanagersequencetaskexecution.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.korobeynikov.p31workmanagersequencetaskexecution.Constants
import java.util.concurrent.TimeUnit

class MyWorker3(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d(Constants.TAG, "${javaClass.simpleName} start")
        TimeUnit.SECONDS.sleep(3)
        Log.d(Constants.TAG, "${javaClass.simpleName} end")
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.d(Constants.TAG, "${javaClass.simpleName} onStopped")
    }
}