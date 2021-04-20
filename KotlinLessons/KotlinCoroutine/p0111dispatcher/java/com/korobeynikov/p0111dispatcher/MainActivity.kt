package com.korobeynikov.p0111dispatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity(){

    private val scope=CoroutineScope(Dispatchers.Unconfined)
    private var job:Job?=null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnRun.setOnClickListener{
            onRun()
        }
    }

    private fun onRun(){
        if(job?.isActive==true)return
        job=scope.launch{
            log("start coroutine")
            val data=getData()
            log("end coroutine")
        }
    }

    private fun updateUI(data:String){
        label.text=data
    }

    private suspend fun getData():String=
        suspendCoroutine{
            log("suspend function, start")
            thread{
                log("suspend function, background work")
                TimeUnit.MILLISECONDS.sleep(1000)
                it.resume("Data!")
            }
        }

    override fun onDestroy(){
        super.onDestroy()
        scope.cancel()
    }

    private fun log(text:String){
        Log.d("TAG","$text [${Thread.currentThread().name}]")
    }
}