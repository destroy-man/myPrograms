package ru.korobeynikov.p0851runnableuithread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0851runnableuithread.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var tvInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        tvInfo = binding.tvInfo
        Thread {
            try {
                TimeUnit.SECONDS.sleep(2)
                runOnUiThread(runn1)
                TimeUnit.SECONDS.sleep(1)
                tvInfo.postDelayed(runn3, 2000)
                tvInfo.post(runn2)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }

    private val runn1 = Runnable {
        tvInfo.text = getText(R.string.runnable_1_text)
    }

    private val runn2 = Runnable {
        tvInfo.text = getText(R.string.runnable_2_text)
    }

    private val runn3 = Runnable {
        tvInfo.text = getText(R.string.runnable_3_text)
    }
}