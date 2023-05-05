package ru.korobeynikov.p1001serviceautoload

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    override fun onCreate() {
        Log.d(MainActivity.LOG_TAG, "MyService onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(MainActivity.LOG_TAG, "MyService onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? = null
}