package ru.korobeynikov.p0401layoutinflater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0401layoutinflater.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val view = layoutInflater.inflate(R.layout.text, binding.conLayout, true)
        Log.d(logTag, "Class of view: ${view.javaClass}")
        Log.d(logTag, "Class of layoutParams of view: ${view.layoutParams.javaClass}")
    }
}