package ru.korobeynikov.p0901asynctaskstatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0901asynctaskstatus.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var executorService: ExecutorService? = null
    private lateinit var tvInfo: TextView
    private lateinit var status: TaskStatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        tvInfo = binding.tvInfo
    }

    fun clickButton(v: View) {
        when (v.id) {
            R.id.btnStart -> startTask()
            R.id.btnStatus -> showStatus()
        }
    }

    private fun startTask() {
        status = TaskStatus.PENDING
        executorService = Executors.newSingleThreadExecutor()
        executorService?.execute {
            status = TaskStatus.RUNNING
            runOnUiThread {
                tvInfo.text = getText(R.string.begin)
            }
            try {
                for (i in 0 until 5)
                    TimeUnit.SECONDS.sleep(1)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            runOnUiThread {
                tvInfo.text = getText(R.string.end)
            }
            status = TaskStatus.FINISHED
        }
    }

    private fun showStatus() {
        if (executorService != null)
            Toast.makeText(this, status.name, Toast.LENGTH_SHORT).show()
    }
}