package ru.korobeynikov.p1421drawingfigure

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(DrawView(this))
    }

    class DrawView(context: Context) : View(context) {

        private val p = Paint()
        private val rect = Rect(100, 200, 200, 300)
        private val sb = StringBuilder()

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            p.color = Color.BLUE
            p.strokeWidth = 10f
            p.textSize = 30f
            sb.clear()
            sb.append("width = $width, height = $height")
            canvas?.drawText(sb.toString(), 100f, 100f, p)
            p.style = Paint.Style.FILL
            canvas?.drawRect(rect, p)
            p.style = Paint.Style.STROKE
            rect.offset(150, 0)
            canvas?.drawRect(rect, p)
            p.style = Paint.Style.FILL_AND_STROKE
            rect.offset(150, 0)
            canvas?.drawRect(rect, p)
        }
    }
}