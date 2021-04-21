package com.korobeynikov.p0131exceptionhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private var formatter=SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
    val handler=CoroutineExceptionHandler {context, exception->
        log("first coroutine exception $exception")
    }
    private val scope=CoroutineScope(SupervisorJob()+Dispatchers.Default+handler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnRun.setOnClickListener{
            onRun()
        }
    }

    private fun onRun(){
        scope.launch{
            TimeUnit.MILLISECONDS.sleep(1000)
            Integer.parseInt("a")
        }
        scope.launch{
            repeat(5){
                TimeUnit.MILLISECONDS.sleep(300)
                log("second coroutine isActive ${isActive}")
            }
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        scope.cancel()
    }

    private fun log(text:String){
        Log.d("TAG","${formatter.format(Date())} $text")
    }
}