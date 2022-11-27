package ru.korobeynikov.p32workmanagertransmissionandreceivedata

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MyWorker2(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d(MyWorker3.TAG, "MyWorker2 start")
        try {
            TimeUnit.SECONDS.sleep(2)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(MyWorker3.TAG, "MyWorker2 end")
        val output = Data.Builder().putString("keyA", "value2").putInt("keyB", 2)
            .putString("keyD", "valueD").build()
        return Result.success(output)
    }

    override fun onStopped() {
        super.onStopped()
        Log.d(MyWorker3.TAG, "onStopped")
    }
}