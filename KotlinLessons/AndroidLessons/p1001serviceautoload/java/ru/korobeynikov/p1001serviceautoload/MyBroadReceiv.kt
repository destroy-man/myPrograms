package ru.korobeynikov.p1001serviceautoload

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyBroadReceiv : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(MainActivity.LOG_TAG, "onReceive ${intent.action}")
        context.startForegroundService(Intent(context, MyService::class.java))
    }
}