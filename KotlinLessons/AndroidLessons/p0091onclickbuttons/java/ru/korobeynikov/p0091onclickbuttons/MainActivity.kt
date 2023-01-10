package ru.korobeynikov.p0091onclickbuttons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0091onclickbuttons.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var tvOut: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        tvOut = binding.tvOut
        binding.view = this
    }

    fun clickButtonOk() {
        tvOut.text = resources.getText(R.string.clickOk)
    }

    fun clickButtonCancel() {
        tvOut.text = resources.getText(R.string.clickCancel)
    }
}