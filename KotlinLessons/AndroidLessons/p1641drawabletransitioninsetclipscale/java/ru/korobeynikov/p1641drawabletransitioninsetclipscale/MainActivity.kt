package ru.korobeynikov.p1641drawabletransitioninsetclipscale

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1641drawabletransitioninsetclipscale.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        imageView = binding.imageView
        setDrawable()
    }

    private fun setDrawable() {
        val drawable = ContextCompat.getDrawable(this, R.drawable.anim) as AnimationDrawable
        drawable.start()
        imageView.setImageDrawable(drawable)
    }
}