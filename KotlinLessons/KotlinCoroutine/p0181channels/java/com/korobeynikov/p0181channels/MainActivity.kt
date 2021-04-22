package com.korobeynikov.p0181channels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var formatter=SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
    private val scope=CoroutineScope(Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scope.launch{
            val channel=Channel<Int>(2)
            launch{
                repeat(7){
                    delay(300)
                    log("send $it")
                    channel.send(it)
                }
                log("cancel")
                channel.cancel()
            }
            launch{
                for(element in channel){
                    log("received $element")
                    delay(1000)
                }
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