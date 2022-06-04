package ru.korobeynikov.p0981servicebindinglocal

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    var bound=false
    lateinit var sConn:ServiceConnection
    lateinit var intentMyService:Intent
    lateinit var myService:MyService
    var interval=0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intentMyService= Intent(this,MyService::class.java)
        sConn=object:ServiceConnection{
            override fun onServiceConnected(p0: ComponentName?, binder: IBinder?) {
                Log.d(LOG_TAG,"MainActivity onServiceConnected")
                myService=(binder as MyService.MyBinder).getService()
                bound=true
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.d(LOG_TAG,"MainActivity onServiceDisconnected")
                bound=false
            }
        }
    }

    protected override fun onStart() {
        super.onStart()
        bindService(intentMyService,sConn,0)
    }

    protected override fun onStop() {
        super.onStop()
        if(!bound)return
        unbindService(sConn)
        bound=false
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(intentMyService)
    }

    fun onClickStart(v:View){
        startService(intentMyService)
    }

    fun onClickUp(v:View){
        if(!bound)return
        interval=myService.upInterval(500)
        tvInterval.text="interval = $interval"
    }

    fun onClickDown(v:View){
        if(!bound)return
        interval=myService.downInterval(500)
        tvInterval.text="interval = $interval"
    }
}