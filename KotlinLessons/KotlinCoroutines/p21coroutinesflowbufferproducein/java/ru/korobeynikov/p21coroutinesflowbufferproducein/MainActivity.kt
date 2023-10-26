package ru.korobeynikov.p21coroutinesflowbufferproducein

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import ru.korobeynikov.p21coroutinesflowbufferproducein.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        CoroutineScope(Job()).launch {
            val flow = channelFlow {
                launch {
                    delay(1000)
                    send(1)
                }
                launch {
                    delay(1000)
                    send(2)
                }
                launch {
                    delay(1000)
                    send(3)
                }
            }
            flow {
                coroutineScope {
                    produce(capacity = 2) {
                        flow.collect {
                            send(it)
                        }
                    }.consumeEach {
                        emit(it)
                    }
                }
            }.collect {
                log(it.toString())
            }
        }
    }

    private fun log(text: String) = Log.d("myLogs", "${formatter.format(Date())} $text")
}