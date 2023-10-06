package ru.korobeynikov.p10cpuprofiler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p10cpuprofiler.databinding.ActivityMainBinding
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun test() {
        val executor = Executors.newFixedThreadPool(2)
        for (i in 0 until 3)
            executor.submit(MyRunnable())
    }
}