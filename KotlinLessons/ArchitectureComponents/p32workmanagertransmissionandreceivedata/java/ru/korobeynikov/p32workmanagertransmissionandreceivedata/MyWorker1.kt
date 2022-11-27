package ru.korobeynikov.p32workmanagertransmissionandreceivedata

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MyWorker1(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d(MyWorker3.TAG, "MyWorker1 start")
        try {
            TimeUnit.SECONDS.sleep(1)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(MyWorker3.TAG, "MyWorker1 end")
        val output = Data.Builder().putString("keyA", "value1").putInt("keyB", 1)
            .putString("keyC", "valueC").build()
        return Result.success(output)
    }

    override fun onStopped() {
        super.onStopped()
        Log.d(MyWorker3.TAG, "onStopped")
    }
}