package com.korobeynikov.p0121relationshipbetweenparentandchildcoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var formatter= SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
    private val scope=CoroutineScope(Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val job=scope.launch{
            log("parent start")
            launch{
                log("child start")
                delay(1000)
                log("child end")
            }
            log("parent end")
        }

        scope.launch{
            delay(500)
            log("parent job is active: ${job.isActive}")
            delay(1000)
            log("parent job is active: ${job.isActive}")
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