package ru.korobeynikov.p12multiscope.other

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p12multiscope.R
import ru.korobeynikov.p12multiscope.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "myLogs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        startActivity(Intent(this, UserActivity::class.java))
    }

    fun goToOrder() {
        startActivity(Intent(this, OrderActivity::class.java))
    }
}