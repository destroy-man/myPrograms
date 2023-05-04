package ru.korobeynikov.p0981servicebindinglocal

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.*

class MyService : Service() {

    private val binder = MyBinder()
    private var interval = 1000L
    private var tTask: TimerTask? = null
    private lateinit var timer: Timer

    override fun onCreate() {
        super.onCreate()
        Log.d(MainActivity.LOG_TAG, "MyService onCreate")
        timer = Timer()
        schedule()
    }

    private fun schedule() {
        if (tTask != null)
            tTask?.cancel()
        if (interval > 0) {
            tTask = object : TimerTask() {
                override fun run() {
                    Log.d(MainActivity.LOG_TAG, "run")
                }
            }
            timer.schedule(tTask, 1000, interval)
        }
    }

    fun upInterval(gap: Long): Long {
        interval += gap
        schedule()
        return interval
    }

    fun downInterval(gap: Long): Long {
        interval -= gap
        if (interval < 0)
            interval = 0
        schedule()
        return interval
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(MainActivity.LOG_TAG, "MyService onBind")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        tTask?.cancel()
        Log.d(MainActivity.LOG_TAG, "MyService onUnbind")
        return super.onUnbind(intent)
    }

    inner class MyBinder : Binder() {
        fun getService() = this@MyService
    }
}