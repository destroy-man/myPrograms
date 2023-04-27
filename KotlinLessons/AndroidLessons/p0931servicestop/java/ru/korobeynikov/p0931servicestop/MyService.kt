package ru.korobeynikov.p0931servicestop

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MyService : Service() {

    val logTag = "myLogs"
    private lateinit var es: ExecutorService
    var someRes: Any? = null

    override fun onCreate() {
        Log.d(logTag, "MyService onCreate")
        es = Executors.newFixedThreadPool(3)
        someRes = Any()
    }

    override fun onDestroy() {
        Log.d(logTag, "MyService onDestroy")
        someRes = null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(logTag, "MyService onStartCommand")
        intent?.let {
            val time = it.getIntExtra("time", 1)
            es.execute(MyRun(time, startId))
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? = null

    inner class MyRun(private val time: Int, private val startId: Int) : Runnable {

        init {
            Log.d(logTag, "MyRun#$startId create")
        }

        override fun run() {
            Log.d(logTag, "MyRun#$startId start, time = $time")
            try {
                TimeUnit.SECONDS.sleep(time.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            someRes?.let {
                Log.d(logTag, "MyRun#$startId someRes = ${it::class}")
            } ?: Log.d(logTag, "MyRun#$startId error, null pointer")
            stop()
        }

        private fun stop() =
            Log.d(logTag, "MyRun#$startId end, stopSelfResult($startId) = ${stopSelfResult(startId)}")
    }
}