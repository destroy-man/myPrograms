package ru.korobeynikov.p0831handlermessagemanage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    lateinit var h:Handler
    val hc=Handler.Callback{msg->
        Log.d(LOG_TAG,"what = ${msg.what}")
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        h=Handler(hc)
        sendMessages()
    }

    fun sendMessages(){
        Log.d(LOG_TAG,"send messages")
        h.sendEmptyMessageDelayed(1,1000)
        h.sendEmptyMessageDelayed(2,2000)
        h.sendEmptyMessageDelayed(3,3000)
        h.sendEmptyMessageDelayed(2,4000)
        h.sendEmptyMessageDelayed(5,5000)
        h.sendEmptyMessageDelayed(2,6000)
        h.sendEmptyMessageDelayed(7,7000)
        h.removeMessages(2)
    }
}