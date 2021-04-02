package com.korobeynikov.p0091builderslaunchandasync

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var formatter=SimpleDateFormat("HH:mm:ss.SSS",Locale.getDefault())
    private val scope=CoroutineScope(Job())
    lateinit var job:Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btnRun).setOnClickListener(){
            onRun()
        }
        findViewById<View>(R.id.btnRun2).setOnClickListener(){
            onRun2()
        }
    }

    private fun onRun(){
        scope.launch(){
            log("parent coroutine, start")
            val data=async{
                getData()
            }
            val data2=async{
                getData2()
            }
            log("parent coroutine, wait until children return result")
            val result="${data.await()}, ${ data2.await()}"
            log("parent coroutine, children returned: $result")
            log("parent coroutine, end")
        }
    }

    private fun onRun2(){
        log("onRun2, start")
        job.start()
        log("onRun2, end")
    }

    private suspend fun getData():String{
        delay(1000)
        return "data"
    }

    private suspend fun getData2():String{
        delay(1500)
        return "data2"
    }

    private fun log(text:String){
        Log.d("TAG","${formatter.format(Date())}, $text [${Thread.currentThread().name}]")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        scope.cancel()
    }
}