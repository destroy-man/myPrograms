package ru.korobeynikov.p09coroutinesbuilderslaunchandasync

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import ru.korobeynikov.p09coroutinesbuilderslaunchandasync.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
    private val scope = CoroutineScope(Job())
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun onRun() {
        scope.launch {
            log("parent coroutine, start")
            val data = async { getData() }
            val data2 = async { getData2() }
            log("parent coroutine, wait until children return result")
            val result = "${data.await()}, ${data2.await()}"
            log("parent coroutine, children returned: $result")
            log("parent coroutine, end")
        }
    }

    private suspend fun getData(): String {
        delay(1000)
        return "data"
    }

    private suspend fun getData2(): String {
        delay(1500)
        return "data2"
    }

    fun onRun2() {
        log("onRun2, start")
        job.start()
        log("onRun2, end")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        scope.cancel()
    }

    private fun log(text: String) =
        Log.d("myLogs", "${formatter.format(Date())} $text [${Thread.currentThread().name}]")
}