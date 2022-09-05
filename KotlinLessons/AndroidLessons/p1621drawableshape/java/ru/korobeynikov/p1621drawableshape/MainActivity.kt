package ru.korobeynikov.p1621drawableshape

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setDrawable()
    }

    private fun setDrawable() {
        val drawable = GradientDrawable(GradientDrawable.Orientation.BL_TR, intArrayOf(Color.RED,
            Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA))
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        drawable.cornerRadius = 40f
        drawable.setStroke(10, Color.BLACK, 20f, 5f)
        imageView.setImageDrawable(drawable)
    }
}