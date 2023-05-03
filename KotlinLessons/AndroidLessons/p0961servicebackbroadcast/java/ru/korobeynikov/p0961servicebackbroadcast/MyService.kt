package ru.korobeynikov.p0961servicebackbroadcast

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
            val task = it.getIntExtra(MainActivity.PARAM_TASK, 0)
            es.execute(MyRun(startId, time, task))
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? = null

    inner class MyRun(private val startId: Int, private val time: Int, private val task: Int) : Runnable {

        init {
            Log.d(MainActivity.LOG_TAG, "MyRun#$startId create")
        }

        override fun run() {
            Log.d(MainActivity.LOG_TAG, "MyRun#$startId start, time = $time")
            try {
                with(Intent(MainActivity.BROADCAST_ACTION)) {
                    putExtra(MainActivity.PARAM_TASK, task)
                    putExtra(MainActivity.PARAM_STATUS, MainActivity.STATUS_START)
                    sendBroadcast(this)
                    TimeUnit.SECONDS.sleep(time.toLong())
                    putExtra(MainActivity.PARAM_STATUS, MainActivity.STATUS_FINISH)
                    putExtra(MainActivity.PARAM_RESULT, time * 100)
                    sendBroadcast(this)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            stop()
        }

        private fun stop() = Log.d(MainActivity.LOG_TAG,
            "MyRun#$startId end, stopSelfResult($startId) = ${stopSelfResult(startId)}")
    }
}