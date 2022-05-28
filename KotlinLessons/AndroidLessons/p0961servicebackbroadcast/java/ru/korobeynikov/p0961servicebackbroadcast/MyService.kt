package ru.korobeynikov.p0961servicebackbroadcast

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
        val task=intent?.getIntExtra(MainActivity.PARAM_TASK,0)
        val mr=MyRun(startId,time,task)
        es.execute(mr)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    inner class MyRun(val startId:Int,val time:Int?,val task:Int?):Runnable{

        init{
            Log.d(LOG_TAG,"MyRun#$startId create")
        }

        override fun run() {
            val intent=Intent(MainActivity.BROADCAST_ACTION)
            Log.d(LOG_TAG,"MyRun#$startId start, time = $time")
            try{
                intent.putExtra(MainActivity.PARAM_TASK,task)
                intent.putExtra(MainActivity.PARAM_STATUS,MainActivity.STATUS_START)
                sendBroadcast(intent)
                if(time!=null)TimeUnit.SECONDS.sleep(time.toLong())
                else TimeUnit.SECONDS.sleep(1)
                intent.putExtra(MainActivity.PARAM_STATUS,MainActivity.STATUS_FINISH)
                if(time!=null)intent.putExtra(MainActivity.PARAM_RESULT,time*100)
                else intent.putExtra(MainActivity.PARAM_RESULT,100)
                sendBroadcast(intent)
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