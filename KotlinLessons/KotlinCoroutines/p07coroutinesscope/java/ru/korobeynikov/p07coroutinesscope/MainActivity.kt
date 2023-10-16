package ru.korobeynikov.p07coroutinesscope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.korobeynikov.p07coroutinesscope.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val tag = "myLogs"
    private val scope = CoroutineScope(Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val job = scope.launch {
            Log.d(tag, "scope = $this")
        }
        Log.d(tag, "job = $job")
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}