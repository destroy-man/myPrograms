package ru.korobeynikov.p23coroutinesscopelivedata

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ru.korobeynikov.p23coroutinesscopelivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        log("Activity onCreate")
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.liveData.observe(this) { log(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("Activity onDestroy, isFinishing = $isFinishing")
    }

    private fun log(text: String) = Log.d("myLogs", text)
}