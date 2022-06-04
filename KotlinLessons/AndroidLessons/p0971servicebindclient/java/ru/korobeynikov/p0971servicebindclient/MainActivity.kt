package ru.korobeynikov.p0971servicebindclient

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    var bound=false
    lateinit var sConn:ServiceConnection
    lateinit var intentService: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intentService=Intent("ru.korobeynikov.p0971servicebindclient.MyService")
        intentService.setPackage("ru.korobeynikov.p0971servicebindclient")
        sConn=object:ServiceConnection{
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                Log.d(LOG_TAG,"MainActivity onServiceConnected")
                bound=true
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.d(LOG_TAG,"MainActivity onServiceDisconnected")
                bound=false
            }
        }
    }

    fun onClickStart(v:View){
        startService(intentService)
    }

    fun onClickStop(v:View){
        stopService(intentService)
    }

    fun onClickBind(v:View){
        bindService(intentService,sConn,0)
    }

    fun onClickUnBind(v:View?){
        if(!bound)return
        unbindService(sConn)
        bound=false
    }

    protected override fun onDestroy() {
        super.onDestroy()
        onClickUnBind(null)
    }
}