package ru.korobeynikov.p32workmanagertransmissionandreceivedata

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MyWorker4(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d(MyWorker3.TAG, "MyWorker4 start")
        val valueA = inputData.getString("keyA")
        val valueB = inputData.getInt("keyB", 0)
        val valueC = inputData.getString("keyC")
        val valueD = inputData.getString("keyD")
        Log.d(MyWorker3.TAG, "valueA = $valueA")
        Log.d(MyWorker3.TAG, "valueB = $valueB")
        Log.d(MyWorker3.TAG, "valueC = $valueC")
        Log.d(MyWorker3.TAG, "valueD = $valueD")
        try {
            TimeUnit.SECONDS.sleep(4)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(MyWorker3.TAG, "MyWorker4 end")
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.d(MyWorker3.TAG, "onStopped")
    }
}