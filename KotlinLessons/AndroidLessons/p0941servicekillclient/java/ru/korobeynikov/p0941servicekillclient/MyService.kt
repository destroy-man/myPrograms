package ru.korobeynikov.p0941servicekillclient

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.TimeUnit

class MyService : Service() {

    val LOG_TAG="myLogs"

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG,"MyService onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG,"MyService onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG_TAG,"MyService onStartCommand, name = ${intent?.getStringExtra("name")}")
        readFlags(flags)
        val mr=MyRun(startId)
        Thread(mr).start()
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    fun readFlags(flags:Int){
        if((flags and START_FLAG_REDELIVERY)==START_FLAG_REDELIVERY)
            Log.d(LOG_TAG,"START_FLAG_REDELIVERY")
        if((flags and START_FLAG_RETRY)==START_FLAG_RETRY)
            Log.d(LOG_TAG,"START_FLAG_RETRY")
        if(flags==0)
            Log.d(LOG_TAG,"0")
    }

    inner class MyRun(val startId:Int):Runnable{

        init{
            Log.d(LOG_TAG,"MyRun#$startId create")
        }

        override fun run() {
            Log.d(LOG_TAG,"MyRun#$startId start")
            try{
                TimeUnit.SECONDS.sleep(15)
            }
            catch(e:InterruptedException){
                e.printStackTrace()
            }
            stop()
        }

        fun stop(){
            Log.d(LOG_TAG,"MyRun#$startId end, stopSelfResult($startId) = ${stopSelfResult(startId)}")
        }
    }
}