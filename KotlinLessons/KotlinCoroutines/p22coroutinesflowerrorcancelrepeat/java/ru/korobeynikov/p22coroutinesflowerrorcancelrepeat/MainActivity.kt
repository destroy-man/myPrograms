package ru.korobeynikov.p22coroutinesflowerrorcancelrepeat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.korobeynikov.p22coroutinesflowerrorcancelrepeat.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        CoroutineScope(Job()).launch {
            flow {
                delay(500)
                emit("1")
                delay(500)
                emit("2")
                delay(500)
                emit("3")
                1 / 0
                delay(500)
                emit("4")
            }.collect {
                if (it == "2") cancel()
                log("collect $it")
            }
        }
    }

    private fun log(text: String) = Log.d("myLogs", "${formatter.format(Date())} $text")
}