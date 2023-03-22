package ru.korobeynikov.p0821handleradvmessage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0821handleradvmessage.databinding.ActivityMainBinding
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

class MainActivity : AppCompatActivity() {

    private val statusNone = 0
    private val statusConnecting = 1
    private val statusConnected = 2
    private val statusDownloadStart = 3
    private val statusDownloadFile = 4
    private val statusDownloadEnd = 5
    private val statusDownloadNone = 6
    private lateinit var executor: MyExecutor
    private lateinit var tvStatus: TextView
    private lateinit var pbDownload: ProgressBar
    private lateinit var btnConnect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        tvStatus = binding.tvStatus
        pbDownload = binding.pbDownload
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
                TimeUnit.SECONDS.sleep(1)
                executor.status = statusConnected
                processStatus()
                TimeUnit.SECONDS.sleep(1)
                val filesCount = Random().nextInt(5)
                if (filesCount == 0) {
                    executor.status = statusDownloadNone
                    processStatus()
                    TimeUnit.MILLISECONDS.sleep(1500)
                    executor.status = statusNone
                    processStatus()
                }
                executor.setData(statusDownloadStart, filesCount)
                processStatus()
                for (i in 1..filesCount) {
                    executor.setData(statusDownloadFile, i, filesCount - i, downloadFile())
                    processStatus()
                }
                executor.status = statusDownloadEnd
                processStatus()
                TimeUnit.MILLISECONDS.sleep(1500)
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
                    tvStatus.text = getText(R.string.none)
                    pbDownload.visibility = View.GONE
                }
                statusConnecting -> {
                    btnConnect.isEnabled = false
                    tvStatus.text = getText(R.string.connecting)
                }
                statusConnected -> tvStatus.text = getText(R.string.connected)
                statusDownloadStart -> {
                    tvStatus.text = getString(R.string.download_start, executor.countFiles)
                    pbDownload.max = executor.countFiles
                    pbDownload.progress = 0
                    pbDownload.visibility = View.VISIBLE
                }
                statusDownloadFile -> {
                    tvStatus.text = getString(R.string.download_file, executor.countFilesLeft)
                    pbDownload.progress = executor.numberFile
                    saveFile(executor.contentFile)
                }
                statusDownloadEnd -> {
                    tvStatus.text = getText(R.string.download_end)
                    pbDownload.progress = executor.numberFile
                }
                statusDownloadNone -> tvStatus.text = getText(R.string.download_none)
            }
        }
    }

    @Throws(InterruptedException::class)
    fun downloadFile(): ByteArray {
        TimeUnit.SECONDS.sleep(2)
        return ByteArray(1024)
    }

    private fun saveFile(file: ByteArray) {}
}