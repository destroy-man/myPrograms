package ru.korobeynikov.p03strictmode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Environment.getExternalStoragePublicDirectory
import android.os.StrictMode
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p03strictmode.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        enableStrictMode()
        doWork()
    }

    private fun enableStrictMode() {
        if (BuildConfig.DEBUG) {
            val threadPolicy = StrictMode.ThreadPolicy.Builder().detectDiskReads().penaltyLog().build()
            StrictMode.setThreadPolicy(threadPolicy)
        }
    }

    private fun doWork() {
        File(getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), "file.txt").exists()
        StrictMode.setThreadPolicy(StrictMode.allowThreadDiskReads())
    }
}