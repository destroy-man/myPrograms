package ru.korobeynikov.p1661createdrawable

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bitmap=BitmapFactory.decodeResource(resources,R.drawable.picture)
        val dr=BitmapHexagonDrawable(bitmap)
        view.setBackgroundDrawable(dr)
    }
}