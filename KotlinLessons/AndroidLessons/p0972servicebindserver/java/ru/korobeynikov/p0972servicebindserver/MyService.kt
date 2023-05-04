package ru.korobeynikov.p0972servicebindserver

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    private val logTag = "myLogs"

    override fun onCreate() {
        Log.d(logTag, "MyService onCreate")
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(logTag, "MyService onBind")
        return Binder()
    }

    override fun onRebind(intent: Intent?) {
        Log.d(logTag, "MyService onRebind")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(logTag, "MyService onUnbind")
        return true
    }

    override fun onDestroy() {
        Log.d(logTag, "MyService onDestroy")
    }
}