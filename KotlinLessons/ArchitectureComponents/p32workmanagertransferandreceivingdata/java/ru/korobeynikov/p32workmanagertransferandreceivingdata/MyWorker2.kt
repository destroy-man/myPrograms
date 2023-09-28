package ru.korobeynikov.p32workmanagertransferandreceivingdata

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class MyWorker2(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.d(MainActivity.TAG, "MyWorker2 start")
        try {
            TimeUnit.SECONDS.sleep(2)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val output =
            Data.Builder().putString("keyA", "value2").putInt("keyB", 2).putString("keyD", "valueD").build()
        Log.d(MainActivity.TAG, "MyWorker2 end")
        return Result.success(output)
    }
}