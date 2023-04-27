package ru.korobeynikov.p0931servicestop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0931servicestop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun clickStart() {
        startService(Intent(this, MyService::class.java).putExtra("time", 7))
        startService(Intent(this, MyService::class.java).putExtra("time", 2))
        startService(Intent(this, MyService::class.java).putExtra("time", 4))
    }
}