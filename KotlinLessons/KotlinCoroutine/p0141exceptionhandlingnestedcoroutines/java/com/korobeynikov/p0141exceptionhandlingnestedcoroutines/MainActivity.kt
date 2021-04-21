package com.korobeynikov.p0141exceptionhandlingnestedcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    private var formatter= SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
    val handler=CoroutineExceptionHandler{context,exception ->
        log("$exception was handled in Coroutine_${context[CoroutineName]?.name}")
    }
    private val scope=CoroutineScope(Job()+Dispatchers.IO+handler)

    fun CoroutineScope.repeatIsActive(){
        repeat(5){
            TimeUnit.MILLISECONDS.sleep(300)
            log("Coroutine_${coroutineContext[CoroutineName]?.name} isActive $isActive")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scope.launch(CoroutineName("1")){
            launch(CoroutineName("1_1")){
                TimeUnit.MILLISECONDS.sleep(1000)
                log("exception")
                Integer.parseInt("a")
            }
            launch(CoroutineName("1_2")){
                repeatIsActive()
            }
            repeatIsActive()
        }
        scope.launch(CoroutineName("2")){
            launch(CoroutineName("2_1")){
                repeatIsActive()
            }
            launch(CoroutineName("2_2")){
                repeatIsActive()
            }
            repeatIsActive()
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