package ru.korobeynikov.p0891asynctaskcancel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0891asynctaskcancel.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"
    private var executorService: ExecutorService? = null
    private lateinit var tvInfo: TextView
    private var cancel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        tvInfo = binding.tvInfo
    }

    private fun cancelTask() {
        if (executorService == null) return
        cancel = true
        Log.d(logTag, "cancel result: $cancel")
    }

    fun clickButton(v: View) {
        when (v.id) {
            R.id.btnStart -> {
                cancel = false
                executorService = Executors.newSingleThreadExecutor()
                executorService?.execute {
                    runOnUiThread {
                        tvInfo.text = getText(R.string.begin)
                    }
                    Log.d(logTag, getString(R.string.begin))
                    try {
                        for (i in 0 until 5) {
                            TimeUnit.SECONDS.sleep(1)
                            if (cancel) break
                            Log.d(logTag, "cancel: $cancel")
                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    if (!cancel) {
                        runOnUiThread {
                            tvInfo.text = getText(R.string.end)
                        }
                        Log.d(logTag, getString(R.string.end))
                    } else {
                        runOnUiThread {
                            tvInfo.text = getText(R.string.cancel)
                        }
                        Log.d(logTag, "Interrupted")
                        Log.d(logTag, getString(R.string.cancel))
                    }
                }
            }
            R.id.btnCancel -> cancelTask()
        }
    }
}