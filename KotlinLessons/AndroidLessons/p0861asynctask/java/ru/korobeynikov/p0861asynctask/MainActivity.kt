package ru.korobeynikov.p0861asynctask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0861asynctask.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var executorService: ExecutorService
    private lateinit var tvInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        tvInfo = binding.tvInfo
    }

    fun clickButton() {
        executorService = Executors.newSingleThreadExecutor()
        executorService.execute {
            runOnUiThread {
                tvInfo.text = getText(R.string.begin)
            }
            try {
                TimeUnit.SECONDS.sleep(2)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            runOnUiThread {
                tvInfo.text = getText(R.string.end)
            }
        }
    }
}