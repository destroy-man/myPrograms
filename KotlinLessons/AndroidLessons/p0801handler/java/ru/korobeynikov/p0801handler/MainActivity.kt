package ru.korobeynikov.p0801handler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0801handler.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"
    private lateinit var executor: MyExecutor
    private lateinit var tvInfo: TextView
    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        tvInfo = binding.tvInfo
        btnStart = binding.btnStart
        executor = MyExecutor()
    }

    fun clickButton(v: View) {
        when (v.id) {
            R.id.btnStart -> {
                btnStart.isEnabled = false
                executor.execute {
                    for (i in 1..10) {
                        downloadFile()
                        executor.countFiles = i
                        runOnUiThread {
                            tvInfo.text = getString(R.string.download_files, executor.countFiles)
                            if (executor.countFiles == 10)
                                btnStart.isEnabled = true
                        }
                        Log.d(logTag, "i = $i")
                    }
                }
            }
            R.id.btnTest -> Log.d(logTag, "test")
        }
    }

    private fun downloadFile() {
        try {
            TimeUnit.SECONDS.sleep(1)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}