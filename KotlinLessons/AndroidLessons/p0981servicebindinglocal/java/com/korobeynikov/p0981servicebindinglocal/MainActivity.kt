package com.korobeynikov.p0981servicebindinglocal

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    var bound=false
    lateinit var sConn:ServiceConnection
    lateinit var intentSevice:Intent
    lateinit var myService:MyService
    var interval by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intentSevice=Intent(this,MyService::class.java)
        sConn=object:ServiceConnection{
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.d(LOG_TAG,"MainActivity onServiceConnected")
                myService=(service as MyService.MyBinder).getService()
                bound=true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.d(LOG_TAG,"MainActivity onServiceDisconnected")
                bound=false
            }

        }
    }

    protected override fun onStart() {
        super.onStart()
        bindService(intentSevice,sConn,0)
    }

    protected override fun onStop() {
        super.onStop()
        if(!bound)return
        unbindService(sConn)
        bound=false
    }

    fun onClickStart(v:View){
        startService(intentSevice)
    }

    fun onClickUp(v:View){
        if(!bound)return
        interval=myService.upInterval(500)
        tvInterval.text="interval = "+interval
    }

    fun onClickDown(v:View){
        if(!bound)return
        interval=myService.downInterval(500)
        tvInterval.text="interval = "+interval
    }
}