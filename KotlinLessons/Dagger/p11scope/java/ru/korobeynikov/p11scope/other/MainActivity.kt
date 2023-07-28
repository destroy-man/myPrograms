package ru.korobeynikov.p11scope.other

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p11scope.R
import ru.korobeynikov.p11scope.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        goToOrder()
    }

    fun goToOrder() {
        startActivity(Intent(this, OrderActivity::class.java))
    }
}