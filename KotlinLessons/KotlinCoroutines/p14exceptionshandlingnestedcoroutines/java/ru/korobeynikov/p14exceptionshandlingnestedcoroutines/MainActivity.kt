package ru.korobeynikov.p14exceptionshandlingnestedcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import ru.korobeynikov.p14exceptionshandlingnestedcoroutines.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        log("$throwable was handled in Coroutine_${coroutineContext[CoroutineName]?.name}")
    }

    private val scope = CoroutineScope(Job() + handler)
    private val formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun onRun() {
        scope.launch(CoroutineName("1")) {
            launch(CoroutineName("1_1")) {
                TimeUnit.MILLISECONDS.sleep(1000)
                log("exception")
                "a".toInt()
            }
            launch(CoroutineName("1_2")) {
                repeatIsActive()
            }
            repeatIsActive()
        }
        scope.launch(CoroutineName("2")) {
            launch(CoroutineName("2_1")) {
                repeatIsActive()
            }
            launch(CoroutineName("2_2")) {
                repeatIsActive()
            }
            repeatIsActive()
        }
    }

    private fun CoroutineScope.repeatIsActive() {
        repeat(5) {
            TimeUnit.MILLISECONDS.sleep(300)
            log("Coroutine_${coroutineContext[CoroutineName]?.name} isActive $isActive")
        }
    }

    private fun log(text: String) = Log.d("myLogs", "${formatter.format(Date())} $text")
}