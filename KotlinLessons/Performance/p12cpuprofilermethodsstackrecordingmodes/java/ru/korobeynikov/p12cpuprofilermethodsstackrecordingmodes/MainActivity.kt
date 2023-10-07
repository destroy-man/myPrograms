package ru.korobeynikov.p12cpuprofilermethodsstackrecordingmodes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p12cpuprofilermethodsstackrecordingmodes.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun test() {
        Debug.startMethodTracing("demo.trace")
        methodA()
        Debug.stopMethodTracing()
    }

    @Throws(InterruptedException::class)
    fun methodA() {
        methodB()
        methodC()
        methodD()
    }

    @Throws(InterruptedException::class)
    fun methodB() = TimeUnit.MILLISECONDS.sleep(50)

    @Throws(InterruptedException::class)
    fun methodC() = Log.d("myLogs", "methodC")

    @Throws(InterruptedException::class)
    fun methodD() = TimeUnit.MILLISECONDS.sleep(50)
}