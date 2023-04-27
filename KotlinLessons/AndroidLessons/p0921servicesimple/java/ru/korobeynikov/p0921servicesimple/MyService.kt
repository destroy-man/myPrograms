package ru.korobeynikov.p0921servicesimple

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.TimeUnit

class MyService : Service() {

    override fun onCreate() {
        Log.d(MainActivity.LOG_TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(MainActivity.LOG_TAG, "onStartCommand")
        someTask()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d(MainActivity.LOG_TAG, "onDestroy")
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(MainActivity.LOG_TAG, "onBind")
        return null
    }

    private fun someTask() {
        Thread {
            for (i in 1..5) {
                Log.d(MainActivity.LOG_TAG, "i = $i")
                try {
                    TimeUnit.SECONDS.sleep(1)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            stopSelf()
        }.start()
    }
}