package ru.korobeynikov.p0881asynctaskresult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0881asynctaskresult.databinding.ActivityMainBinding
import java.util.concurrent.*

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"
    private var returnValue = -1
    private var executorService: ExecutorService? = null
    private lateinit var tvInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        tvInfo = binding.tvInfo
    }

    fun clickButton(v: View) {
        when (v.id) {
            R.id.btnStart -> {
                returnValue = -1
                executorService = Executors.newSingleThreadExecutor()
                executorService?.execute {
                    runOnUiThread {
                        tvInfo.text = getText(R.string.begin)
                    }
                    Log.d(logTag, getString(R.string.begin))
                    try {
                        TimeUnit.SECONDS.sleep(5)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    returnValue = 100500
                    runOnUiThread {
                        tvInfo.text = getString(R.string.end, returnValue)
                    }
                    Log.d(logTag, getString(R.string.end, returnValue))
                }
            }
            R.id.btnGet -> showResult()
        }
    }

    private fun showResult() {
        if (executorService == null) return
        try {
            Log.d(logTag, "Try to get result")
            TimeUnit.SECONDS.sleep(1)
            if (returnValue == -1)
                throw TimeoutException()
            Log.d(logTag, "get returns $returnValue")
            Toast.makeText(this, "get returns $returnValue", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            when (e) {
                is InterruptedException, is ExecutionException -> e.printStackTrace()
                is TimeoutException -> {
                    Log.d(logTag, "get timeout, result = $returnValue")
                    e.printStackTrace()
                }
            }
        }
    }
}