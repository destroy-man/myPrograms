package ru.korobeynikov.p0942servicekillserver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.TimeUnit

class MyService : Service() {

    val logTag = "myLogs"

    override fun onCreate() {
        Log.d(logTag, "MyService onCreate")
    }

    override fun onDestroy() {
        Log.d(logTag, "MyService onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(logTag, "MyService onStartCommand, name = ${intent?.getStringExtra("name")}")
        readFlags(flags)
        Thread(MyRun(startId)).start()
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent): IBinder? = null

    private fun readFlags(flags: Int) {
        if ((flags and START_FLAG_REDELIVERY) == START_FLAG_REDELIVERY)
            Log.d(logTag, "START_FLAG_REDELIVERY")
        if ((flags and START_FLAG_RETRY) == START_FLAG_RETRY)
            Log.d(logTag, "START_FLAG_RETRY")
    }

    inner class MyRun(private val startId: Int) : Runnable {

        init {
            Log.d(logTag, "MyRun#$startId create")
        }

        override fun run() {
            Log.d(logTag, "MyRun#$startId start")
            try {
                TimeUnit.SECONDS.sleep(15)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            stop()
        }

        private fun stop() =
            Log.d(logTag, "MyRun#$startId end, stopSelfResult($startId) = ${stopSelfResult(startId)}")
    }
}