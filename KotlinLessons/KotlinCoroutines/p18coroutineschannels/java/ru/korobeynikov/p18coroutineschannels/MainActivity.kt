package ru.korobeynikov.p18coroutineschannels

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.korobeynikov.p18coroutineschannels.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val scope = CoroutineScope(Job())
        val channel = Channel<Int>(2)
        scope.launch {
            repeat(7) {
                delay(300)
                log("send $it")
                channel.send(it)
            }
            log("cancel")
            channel.cancel()
        }
        scope.launch {
            for (element in channel) {
                log("received $element")
                delay(1000)
            }
        }
    }

    private fun log(text: String) = Log.d("myLogs", "${formatter.format(Date())} $text")
}