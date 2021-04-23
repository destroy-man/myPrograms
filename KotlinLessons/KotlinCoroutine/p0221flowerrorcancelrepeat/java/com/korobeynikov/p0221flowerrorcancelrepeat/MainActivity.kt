package com.korobeynikov.p0221flowerrorcancelrepeat

import android.accounts.NetworkErrorException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private val scope=CoroutineScope(Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var flow=flow{
            delay(500)
            emit("1")
            delay(500)
            emit("2")
            delay(500)
            emit("3")
            val a=1/0
            delay(500)
            emit("4")
        }

        scope.launch{
            launch{
                flow.collect {
                    if(it=="3")cancel()
                    log("collect $it")
                }
            }
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        scope.cancel()
    }

    private fun log(text:String){
        Log.d("TAG","$text")
    }
}