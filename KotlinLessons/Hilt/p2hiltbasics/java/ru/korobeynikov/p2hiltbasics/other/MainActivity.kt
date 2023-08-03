package ru.korobeynikov.p2hiltbasics.other

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p2hiltbasics.R
import ru.korobeynikov.p2hiltbasics.databinding.ActivityMainBinding
import ru.korobeynikov.p2hiltbasics.order.OrderActivity
import ru.korobeynikov.p2hiltbasics.user.UserActivity

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