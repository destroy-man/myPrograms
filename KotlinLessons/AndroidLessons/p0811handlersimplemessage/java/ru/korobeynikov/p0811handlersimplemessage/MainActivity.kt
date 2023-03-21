package ru.korobeynikov.p0811handlersimplemessage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0811handlersimplemessage.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val statusNone = 0
    private val statusConnecting = 1
    private val statusConnected = 2
    private lateinit var executor: MyExecutor
    private lateinit var tvStatus: TextView
    private lateinit var pbConnect: ProgressBar
    private lateinit var btnConnect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        tvStatus = binding.tvStatus
        pbConnect = binding.pbConnect
        btnConnect = binding.btnConnect
        executor = MyExecutor()
        executor.execute {
            processStatus()
        }
    }

    fun clickButton() {
        executor.execute {
            try {
                executor.status = statusConnecting
                processStatus()
                TimeUnit.SECONDS.sleep(2)
                executor.status = statusConnected
                processStatus()
                TimeUnit.SECONDS.sleep(3)
                executor.status = statusNone
                processStatus()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun processStatus() {
        runOnUiThread {
            when (executor.status) {
                statusNone -> {
                    btnConnect.isEnabled = true
                    tvStatus.text = getText(R.string.not_connected)
                }
                statusConnecting -> {
                    btnConnect.isEnabled = false
                    pbConnect.visibility = View.VISIBLE
                    tvStatus.text = getText(R.string.connecting)
                }
                statusConnected -> {
                    pbConnect.visibility = View.GONE
                    tvStatus.text = getText(R.string.connected)
                }
            }
        }
    }
}