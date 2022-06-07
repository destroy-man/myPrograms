package ru.korobeynikov.p1001intentservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class MyBroadReceiv : BroadcastReceiver() {

    val LOG_TAG="myLogs"

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(LOG_TAG,"onReceive ${intent.action}")
        val intentMyService=Intent(context,MyService::class.java)
        intentMyService.putExtra("label","Boot complete")
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
            context.startForegroundService(intentMyService)
        else
            context.startService(intentMyService)
    }
}