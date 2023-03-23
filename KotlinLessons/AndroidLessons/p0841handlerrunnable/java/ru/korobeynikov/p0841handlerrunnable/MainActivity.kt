package ru.korobeynikov.p0841handlerrunnable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0841handlerrunnable.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var executor: MyExecutor
    lateinit var pbCount: ProgressBar
    lateinit var tvInfo: TextView
    lateinit var cbInfo: CheckBox
    var cnt = 0
    val logTag = "myLogs"
    val max = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        executor = MyExecutor()
        pbCount = binding.pbCount
        pbCount.max = max
        pbCount.progress = 0
        tvInfo = binding.tvInfo
        cbInfo = binding.chbInfo
        executor.execute {
            try {
                for (i in 1..max) {
                    cnt = i
                    TimeUnit.MILLISECONDS.sleep(100)
                    updateProgress.run()
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    fun checkedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            tvInfo.visibility = View.VISIBLE
            executor.execute(showInfo)
        } else
            tvInfo.visibility = View.GONE
    }

    private val updateProgress = Runnable {
        pbCount.progress = cnt
    }

    private val showInfo = object : Runnable {
        override fun run() {
            Log.d(logTag, "showInfo")
            runOnUiThread {
                tvInfo.text = getString(R.string.tv_info_text, cnt)
            }
            TimeUnit.MILLISECONDS.sleep(100)
            if (cbInfo.isChecked && pbCount.progress < max)
                this.run()
        }
    }
}