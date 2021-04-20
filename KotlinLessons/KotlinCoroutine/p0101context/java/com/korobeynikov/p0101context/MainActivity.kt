package com.korobeynikov.p0101context

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(){

    private val userData=UserData(1,"name1",10)
    private val scope=CoroutineScope(Job()+Dispatchers.Main+userData)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        log("scope, ${contextToString(scope.coroutineContext)}")
        scope.launch{
            log("coroutine, level1, ${contextToString(coroutineContext)}")
            launch(Dispatchers.Default){
                log("coroutine, level2, ${contextToString(coroutineContext)}")
                launch{
                    log("coroutine, level3, ${contextToString(coroutineContext)}, ${userData}")
                }
            }
        }
    }

    private fun contextToString(context:CoroutineContext):String=
        "Job = ${context[Job]}, Dispatcher = ${context[ContinuationInterceptor]}"

    override fun onDestroy(){
        super.onDestroy()
        log("onDestroy")
        scope.cancel()
    }

    private fun log(text:String){
        Log.d("TAG","$text")
    }
}