package ru.korobeynikov.p32workmanagertransferandreceivingdata

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MyWorker1(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.d(MainActivity.TAG, "MyWorker1 start")
        try {
            TimeUnit.SECONDS.sleep(1)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val output =
            Data.Builder().putString("keyA", "value1").putInt("keyB", 1).putString("keyC", "valueC").build()
        Log.d(MainActivity.TAG, "MyWorker1 end")
        return Result.success(output)
    }
}