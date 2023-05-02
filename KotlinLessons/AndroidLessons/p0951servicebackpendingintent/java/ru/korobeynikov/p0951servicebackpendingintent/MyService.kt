package ru.korobeynikov.p0951servicebackpendingintent

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MyService : Service() {

    private lateinit var es: ExecutorService

    override fun onCreate() {
        Log.d(MainActivity.LOG_TAG, "MyService onCreate")
        es = Executors.newFixedThreadPool(2)
    }

    override fun onDestroy() {
        Log.d(MainActivity.LOG_TAG, "MyService onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(MainActivity.LOG_TAG, "MyService onStartCommand")
        intent?.let {
            val time = it.getIntExtra(MainActivity.PARAM_TIME, 1)
            val pi = it.getParcelableExtra<PendingIntent>(MainActivity.PARAM_PINTENT)
            es.execute(MyRun(time, startId, pi))
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? = null

    inner class MyRun(private val time: Int, private val startId: Int,
                      private val pi: PendingIntent?) : Runnable {

        init {
            Log.d(MainActivity.LOG_TAG, "MyRun#$startId create")
        }

        override fun run() {
            Log.d(MainActivity.LOG_TAG, "MyRun#$startId start, time = $time")
            try {
                pi?.send(MainActivity.STATUS_START)
                TimeUnit.SECONDS.sleep(time.toLong())
                val intent = Intent().putExtra(MainActivity.PARAM_RESULT, time * 100)
                pi?.send(this@MyService, MainActivity.STATUS_FINISH, intent)
            } catch (e: Exception) {
                when (e) {
                    is InterruptedException, is PendingIntent.CanceledException -> e.printStackTrace()
                }
            }
            stop()
        }

        private fun stop() = Log.d(MainActivity.LOG_TAG,
            "MyRun#$startId end, stopSelfResult($startId) = ${stopSelfResult(startId)}")
    }
}