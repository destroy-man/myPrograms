package ru.korobeynikov.p1191pendingintent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(MainActivity.LOG_TAG, "onReceive")
        Log.d(MainActivity.LOG_TAG, "action = ${intent.action}")
        Log.d(MainActivity.LOG_TAG, "extra = ${intent.getStringExtra("extra")}")
    }
}