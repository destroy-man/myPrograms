package com.korobeynikov.p0081cancel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(){

    private var formatter=SimpleDateFormat("HH:mm:ss.SSS",Locale.getDefault())
    private val scope=CoroutineScope(Job())
    private lateinit var job:Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRun.setOnClickListener{
            onRun()
        }
        btnCancel.setOnClickListener{
            onCancel()
        }
    }

    private fun onRun(){
        log("onRun, start")
        job=scope.launch{
            log("coroutine, start")
            var x=0
            while(x<5 && isActive){
                delay(1000)
                log("coroutine, ${x++}, isActive = ${isActive}")
            }
            log("coroutine, end")
        }
        log("onRun, end")
    }

    private fun onCancel(){
        log("onCancel")
        job.cancel()
    }

    override fun onDestroy(){
        super.onDestroy()
        log("onDestroy")
        scope.cancel()
    }

    private fun log(text:String){
        Log.d("TAG","${formatter.format(Date())} $text [${Thread.currentThread().name}]")
    }
}