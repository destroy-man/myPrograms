package ru.korobeynikov.p12parentandchildcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.korobeynikov.p12parentandchildcoroutines.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val scope = CoroutineScope(Job())
        val job = scope.launch {
            log("parent start")
            launch {
                log("child start")
                delay(1000)
                log("child end")
            }
            log("parent end")
        }
        scope.launch {
            delay(500)
            log("parent job is active: ${job.isActive}")
            delay(1000)
            log("parent job is active: ${job.isActive}")
        }
    }

    private fun log(text: String) = Log.d("myLogs", "${formatter.format(Date())} $text")
}