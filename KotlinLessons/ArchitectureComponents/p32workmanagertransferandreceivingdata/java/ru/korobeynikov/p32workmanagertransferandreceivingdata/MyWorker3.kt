package ru.korobeynikov.p32workmanagertransferandreceivingdata

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MyWorker3(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.d(MainActivity.TAG, "MyWorker3 start")
        try {
            TimeUnit.SECONDS.sleep(3)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val valueA = inputData.getString("keyA")
        val valueB = inputData.getInt("keyB", 0)
        val valueC = inputData.getString("keyC")
        val valueD = inputData.getString("keyD")
        Log.d(MainActivity.TAG,
            "valueA = $valueA\nvalue B = $valueB\nvalueC = $valueC\nvalueD = $valueD")
        Log.d(MainActivity.TAG, "MyWorker3 end")
        return Result.success()
    }
}