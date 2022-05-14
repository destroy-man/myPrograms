package ru.korobeynikov.p0931servicestop

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MyService : Service() {

    val LOG_TAG="myLogs"
    lateinit var es:ExecutorService
    var someRes:Any?=null

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG,"MyService onCreate")
        es=Executors.newFixedThreadPool(3)
        someRes=Any()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG,"MyService onDestroy")
        someRes=null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG_TAG,"MyService onStartCommand")
        val time=intent?.getIntExtra("time",1)
        val mr=MyRun(time,startId)
        es.execute(mr)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    inner class MyRun(var time:Int?,var startId:Int):Runnable{

        init{
            Log.d(LOG_TAG,"MyRun#$startId create")
        }

        override fun run() {
            Log.d(LOG_TAG,"MyRun#$startId start, time = $time")
            try{
                if(time!=null)TimeUnit.SECONDS.sleep(time!!.toLong())
            }
            catch(e:InterruptedException){
                e.printStackTrace()
            }
            try{
                if(someRes!=null)Log.d(LOG_TAG,"MyRun#$startId someRes = ${someRes!!::class}")
                else Log.d(LOG_TAG,"MyRun#$startId error, null pointer")
            }
            catch(e:NullPointerException){
                Log.d(LOG_TAG,"MyRun#$startId error, null pointer")
            }
            stop()
        }

        fun stop(){
            Log.d(LOG_TAG,"MyRun#$startId end, stopSelfResult($startId) = ${stopSelfResult(startId)}")
            stopSelf(startId)
        }
    }
}