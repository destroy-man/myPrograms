package com.korobeynikov.p0951servicebackpendingintent

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class MyService : Service() {

    val LOG_TAG="myLogs"
    lateinit var es:ExecutorService

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG,"MyService onCreate")
        es=Executors.newFixedThreadPool(2)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG,"MyService onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG_TAG,"MyService onStartCommand")
        val time=intent?.getIntExtra(MainActivity.PARAM_TIME,1)
        val pi:PendingIntent=intent!!.getParcelableExtra(MainActivity.PARAM_PINTENT)
        val mr=MyRun(time,startId,pi)
        es.execute(mr)
        return super.onStartCommand(intent, flags, startId )
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    inner class MyRun:Runnable{

        var time:Int?
        var startId by Delegates.notNull<Int>()
        lateinit var pi:PendingIntent

        constructor(time:Int?,startId:Int,pi:PendingIntent){
            this.time=time
            this.startId=startId
            this.pi=pi
            Log.d(LOG_TAG,"MyRun#"+startId+" create")
        }

        override fun run() {
            Log.d(LOG_TAG,"MyRun#"+startId+" start, time = "+time)
            try{
                pi.send(MainActivity.STATUS_START)
                TimeUnit.SECONDS.sleep(time!!.toLong())
                val intent=Intent().putExtra(MainActivity.PARAM_RESULT,time!!*100)
                pi.send(this@MyService,MainActivity.STATUS_FINISH,intent)
            }
            catch(e:InterruptedException){
                e.printStackTrace()
            }
            catch(e:PendingIntent.CanceledException){
                e.printStackTrace()
            }
            stop()
        }

        fun stop(){
            Log.d(LOG_TAG,"MyRun#"+startId+" end, stopSelpResult("+startId+") = "+stopSelfResult(startId))
        }
    }
}