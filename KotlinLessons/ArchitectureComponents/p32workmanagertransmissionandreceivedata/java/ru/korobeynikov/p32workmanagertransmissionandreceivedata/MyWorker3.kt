package ru.korobeynikov.p32workmanagertransmissionandreceivedata

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MyWorker3(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        const val TAG = "workmng"
    }

    override fun doWork(): Result {
        Log.d(TAG, "MyWorker3 start")
        val valueA = inputData.getStringArray("keyA")
        val valueB = inputData.getIntArray("keyB")
        val valueC = inputData.getStringArray("keyC")
        val valueD = inputData.getStringArray("keyD")
        Log.d(TAG, "valueA = ${valueA?.toList()}")
        Log.d(TAG, "valueB = ${valueB?.toList()}")
        Log.d(TAG, "valueC = ${valueC?.toList()}")
        Log.d(TAG, "valueD = ${valueD?.toList()}")
        try {
            TimeUnit.SECONDS.sleep(3)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(TAG, "MyWorker3 end")
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.d(TAG, "onStopped")
    }
}