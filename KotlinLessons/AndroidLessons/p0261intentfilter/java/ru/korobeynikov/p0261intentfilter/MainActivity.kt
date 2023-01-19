package ru.korobeynikov.p0261intentfilter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0261intentfilter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun clickButton(v: View) {
        when (v.id) {
            R.id.btnTime -> startActivity(Intent("ru.korobeynikov.intent.action.showtime"))
            R.id.btnDate -> startActivity(Intent("ru.korobeynikov.intent.action.showdate"))
        }
    }
}