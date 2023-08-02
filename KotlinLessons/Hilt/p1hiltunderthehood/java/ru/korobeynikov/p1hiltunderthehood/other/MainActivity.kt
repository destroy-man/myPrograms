package ru.korobeynikov.p1hiltunderthehood.other

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1hiltunderthehood.R
import ru.korobeynikov.p1hiltunderthehood.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "myLogs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        startActivity(Intent(this, OrderActivity::class.java))
    }

    fun goToUser() {
        startActivity(Intent(this, UserActivity::class.java))
    }
}