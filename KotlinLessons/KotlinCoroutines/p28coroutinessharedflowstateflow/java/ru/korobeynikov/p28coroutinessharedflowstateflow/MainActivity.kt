package ru.korobeynikov.p28coroutinessharedflowstateflow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.korobeynikov.p28coroutinessharedflowstateflow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var scope: CoroutineScope
    private lateinit var progress: MutableStateFlow<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        progress = MutableStateFlow(0)
        scope = CoroutineScope(Job())
        scope.launch {
            progress.asStateFlow().collect {
                log("$it")
            }
        }
    }

    fun emitToEventBus() {
        scope.launch {
            log("${progress.replayCache}")
            delay(5000)
            progress.value += 10
        }
    }

    private fun log(text: String) = Log.d("myLogs", text)

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}