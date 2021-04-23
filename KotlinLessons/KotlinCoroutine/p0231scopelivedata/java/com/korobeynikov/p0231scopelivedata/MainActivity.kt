package com.korobeynikov.p0231scopelivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val scope=CoroutineScope(Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch{
            while(true){
                delay(1000)
                log("work")
            }
        }
    }

    override fun onDestroy(){
        super.onDestroy()
    }

    private fun log(text:String){
        Log.d("TAG","$text")
    }
}