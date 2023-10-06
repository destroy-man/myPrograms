package ru.korobeynikov.p11cpuprofilermethodsstack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p11cpuprofilermethodsstack.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun test() = methodA()

    @Throws(InterruptedException::class)
    fun methodA() {
        methodB()
        methodG()
    }

    @Throws(InterruptedException::class)
    fun methodB() = TimeUnit.MILLISECONDS.sleep(200)

    @Throws(InterruptedException::class)
    fun methodC() = TimeUnit.MILLISECONDS.sleep(100)

    @Throws(InterruptedException::class)
    fun methodD() = TimeUnit.MILLISECONDS.sleep(300)

    @Throws(InterruptedException::class)
    fun methodE() = TimeUnit.MILLISECONDS.sleep(150)

    private fun methodG() {
        var d = 0.0
        var time = Int.MAX_VALUE
        for (i in 0 until 10000000) {
            d += time / (time - 1)
            time--
        }
    }
}