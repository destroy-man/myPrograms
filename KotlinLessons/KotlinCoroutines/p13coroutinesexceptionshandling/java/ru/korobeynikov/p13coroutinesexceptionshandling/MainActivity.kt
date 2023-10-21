package ru.korobeynikov.p13coroutinesexceptionshandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import ru.korobeynikov.p13coroutinesexceptionshandling.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        log("first coroutine exception $throwable")
    }

    private val scope = CoroutineScope(SupervisorJob() + handler)
    private val formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun onRun() {
        scope.launch {
            TimeUnit.MILLISECONDS.sleep(1000)
            "a".toInt()
        }
        scope.launch {
            repeat(5) {
                TimeUnit.MILLISECONDS.sleep(300)
                log("second coroutine isActive $isActive")
            }
        }
    }

    private fun log(text: String) = Log.d("myLogs", "${formatter.format(Date())} $text")
}