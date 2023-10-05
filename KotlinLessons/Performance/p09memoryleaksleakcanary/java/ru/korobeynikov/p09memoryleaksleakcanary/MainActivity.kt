package ru.korobeynikov.p09memoryleaksleakcanary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p09memoryleaksleakcanary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun test() = startActivity(Intent(this, TestActivity::class.java))
}