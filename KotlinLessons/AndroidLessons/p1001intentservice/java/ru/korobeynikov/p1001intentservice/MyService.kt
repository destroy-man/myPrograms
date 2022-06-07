package ru.korobeynikov.p1001intentservice

import android.app.IntentService
import android.content.Intent
import android.util.Log
import java.util.concurrent.TimeUnit

class MyService : IntentService("myname") {

    val LOG_TAG="myLogs"

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG,"onCreate")
    }

    protected override fun onHandleIntent(intent: Intent?) {
        val tm=intent?.getIntExtra("time",0)
        val label=intent?.getStringExtra("label")
        Log.d(LOG_TAG,"onHandleIntent start $label")
        try{
            if(tm!=null)
                TimeUnit.SECONDS.sleep(tm.toLong())
            else
                TimeUnit.SECONDS.sleep(0)
        }
        catch(e:InterruptedException){
            e.printStackTrace()
        }
        Log.d(LOG_TAG,"onHandleIntent end $label")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG,"onDestroy")
    }
}