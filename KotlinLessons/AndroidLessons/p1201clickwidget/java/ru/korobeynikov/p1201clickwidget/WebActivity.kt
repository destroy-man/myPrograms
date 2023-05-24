package ru.korobeynikov.p1201clickwidget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1201clickwidget.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityWebBinding>(this, R.layout.activity_web)
        binding.web.loadUrl("https://www.google.com")
    }
}