package ru.korobeynikov.p1491canvastext

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DrawView(this))
    }

    private class DrawView(context: Context) : View(context) {

        private val p = Paint(Paint.ANTI_ALIAS_FLAG)
        private val text = "Test text"
        private val fontSize = 100
        private val pos = floatArrayOf(100f, 300f, 200f, 150f, 300f, 500f, 400f, 300f, 500f, 250f,
            600f, 350f, 700f, 400f, 800f, 200f, 900f, 500f)

        init {
            p.textSize = fontSize.toFloat()
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            canvas?.drawPosText(text, pos, p)
        }
    }
}