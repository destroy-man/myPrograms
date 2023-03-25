package ru.korobeynikov.p0871asynctaskparams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0871asynctaskparams.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

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
        val urls = arrayOf("file_path_1", "file_path_2", "file_path_3", "file_path_4")
        executorService = Executors.newSingleThreadExecutor()
        executorService.execute {
            runOnUiThread {
                tvInfo.text = getText(R.string.begin)
            }
            try {
                var cnt = 0
                for (url in urls) {
                    downloadFile(url)
                    runOnUiThread {
                        tvInfo.text = getString(R.string.downloaded_files, ++cnt)
                    }
                }
                TimeUnit.SECONDS.sleep(1)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            runOnUiThread {
                tvInfo.text = getText(R.string.end)
            }
        }
    }

    @Throws(InterruptedException::class)
    fun downloadFile(url: String) {
        TimeUnit.SECONDS.sleep(2)
    }
}