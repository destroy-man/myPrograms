package com.korobeynikov.p0071scope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(){

    private val TAG="myLogs"
    private val scope=CoroutineScope(Job())

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val job=scope.launch{
            Log.d(TAG,"scope = $this")
        }
        Log.d(TAG,"job = $job")
    }

    override fun onDestroy(){
        super.onDestroy()
        scope.cancel()
    }
}