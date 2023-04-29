package ru.korobeynikov.p0941servicekillclient

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0941servicekillclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun clickStart() {
        val service =
            Intent("ru.korobeynikov.p0942servicekillserver.MyService").putExtra("name", "value")
        service.component = ComponentName("ru.korobeynikov.p0942servicekillserver",
            "ru.korobeynikov.p0942servicekillserver.MyService")
        applicationContext.startForegroundService(service)
    }
}