package ru.korobeynikov.p0981servicebindinglocal

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.*

class MyService : Service() {

    val LOG_TAG="myLogs"
    val binder=MyBinder()
    lateinit var timer:Timer
    var tTask:TimerTask?=null
    var interval=1000L

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG,"MyService onCreate")
        timer=Timer()
        schedule()
    }

    fun schedule(){
        if(tTask!=null)
            tTask?.cancel()
        if(interval>0){
            tTask=object:TimerTask(){
                override fun run() {
                    Log.d(LOG_TAG,"run")
                }
            }
            timer.schedule(tTask,1000,interval)
        }
    }

    fun upInterval(gap:Long):Long{
        interval+=gap
        schedule()
        return interval
    }

    fun downInterval(gap:Long):Long{
        interval-=gap
        if(interval<0)interval=0
        schedule()
        return interval
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(LOG_TAG,"MyService onBind")
        return binder
    }

    inner class MyBinder:Binder(){
        fun getService():MyService{
            return this@MyService
        }
    }
}