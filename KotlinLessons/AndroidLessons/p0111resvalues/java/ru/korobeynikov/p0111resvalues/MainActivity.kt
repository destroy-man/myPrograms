package ru.korobeynikov.p0111resvalues

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0111resvalues.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.llBottom.setBackgroundResource(R.color.llBottomColor)
        binding.tvBottom.text = resources.getString(R.string.tvBottomText)
        binding.btnBottom.text = resources.getText(R.string.btnBottomText)
    }
}