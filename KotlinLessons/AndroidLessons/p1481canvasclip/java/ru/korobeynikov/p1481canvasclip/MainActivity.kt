package ru.korobeynikov.p1481canvasclip

import android.content.Context
import android.graphics.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DrawView(this))
    }

    private class DrawView(context: Context) : View(context) {

        private val p = Paint()
        private val rect1 = Rect(180, 220, 340, 380)
        private val rect2 = Rect(280, 320, 440, 480)
        private val op = Region.Op.DIFFERENCE

        init {
            p.style = Paint.Style.STROKE
            p.strokeWidth = 3f
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            p.color = Color.BLUE
            drawGrid(canvas)
            p.color = Color.RED
            canvas?.drawRect(rect1, p)
            canvas?.drawRect(rect2, p)
            canvas?.translate(600f, 0f)
            canvas?.clipRect(rect1)
            canvas?.clipRect(rect2, op)
            p.color = Color.BLUE
            drawGrid(canvas)
        }

        private fun drawGrid(canvas: Canvas?) {
            for (i in 25 until 400 step 25)
                canvas?.drawLine(100f + i, 100f, 100f + i, 600f, p)
            for (i in 25 until 500 step 25)
                canvas?.drawLine(100f, 100f + i, 500f, 100f + i, p)
        }
    }
}