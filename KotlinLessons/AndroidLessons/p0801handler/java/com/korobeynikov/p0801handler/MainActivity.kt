package com.korobeynikov.p0801handler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    lateinit var h:Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        h=object:Handler(){
            override fun handleMessage(msg: Message) {
                tvInfo.text="Закачано файлов: "+msg.what
                if(msg.what==10)btnStart.isEnabled=true
            }
        }
    }

    fun onclick(v:View){
        when(v.id){
            R.id.btnStart->{
                btnStart.isEnabled=false
                val t=Thread(
                    Runnable{
                        for(i in 1..10){
                            downloadFile()
                            h.sendEmptyMessage(i)
                            Log.d(LOG_TAG,"i = "+i)
                        }
                    }
                )
                t.start()
            }
            R.id.btnTest->Log.d(LOG_TAG,"test")
        }
    }

    fun downloadFile(){
        try{
            TimeUnit.SECONDS.sleep(1)
        }
        catch(e:InterruptedException){
            e.printStackTrace()
        }
    }
}