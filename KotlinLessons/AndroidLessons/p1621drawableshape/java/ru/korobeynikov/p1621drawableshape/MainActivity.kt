package ru.korobeynikov.p1621drawableshape

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1621drawableshape.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        imageView = binding.imageView
        setDrawable()
    }

    private fun setDrawable() {
        val drawable = GradientDrawable(GradientDrawable.Orientation.BL_TR,
            intArrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA)).apply {
            shape = GradientDrawable.RECTANGLE
            gradientType = GradientDrawable.LINEAR_GRADIENT
            cornerRadius = 40f
            setStroke(10, Color.BLACK, 20f, 5f)
        }
        imageView.setImageDrawable(drawable)
    }
}