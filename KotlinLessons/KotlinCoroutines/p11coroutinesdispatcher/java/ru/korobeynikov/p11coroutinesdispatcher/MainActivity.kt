package ru.korobeynikov.p11coroutinesdispatcher

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import ru.korobeynikov.p11coroutinesdispatcher.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.Unconfined)
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun onRun() {
        if (job?.isActive == true) return
        job = scope.launch {
            log("start coroutine")
            getData()
            log("end coroutine")
        }
    }

    private suspend fun getData() = suspendCoroutine {
        log("suspend function, start")
        thread {
            log("suspend function, background work")
            TimeUnit.MILLISECONDS.sleep(1000)
            it.resume("Data!")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun log(text: String) = Log.d("myLogs", "$text [${Thread.currentThread().name}]")
}