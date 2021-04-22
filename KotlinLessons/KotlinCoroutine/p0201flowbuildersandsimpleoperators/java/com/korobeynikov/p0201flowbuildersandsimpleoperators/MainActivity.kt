package com.korobeynikov.p0201flowbuildersandsimpleoperators

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val scope=CoroutineScope(Job())

    fun Flow<String>.toUpperCase():Flow<String> = flow{
        collect{
            emit(it.toUpperCase())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flowString=flow{
            emit("abc")
            emit("def")
            emit("ghi")
        }
        scope.launch{
            flowString.toUpperCase().collect{
                log(it)
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