package ru.korobeynikov.p1661createowndrawable

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1661createowndrawable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.picture)
        binding.view.background = BitmapHexagonDrawable(bitmap)
    }
}