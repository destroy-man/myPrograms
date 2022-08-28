package ru.korobeynikov.p1461canvastransform

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
        private val rectf1 = RectF(50f, 50f, 100f, 100f)
        private val rectf2 = RectF(50f, 150f, 100f, 200f)

        init {
            p.strokeWidth = 3f
            p.style = Paint.Style.STROKE
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            val initSave = canvas?.save()
            p.color = Color.GREEN
            canvas?.drawRect(rectf1, p)
            canvas?.translate(100f, 0f)
            canvas?.drawRect(rectf1, p)
            canvas?.translate(100f, 0f)
            canvas?.drawRect(rectf1, p)
            canvas?.save()
            p.color = Color.YELLOW
            canvas?.translate(100f, 0f)
            canvas?.drawRect(rectf1, p)
            canvas?.translate(100f, 0f)
            canvas?.drawRect(rectf1, p)
            val needSave = canvas?.save()
            p.color = Color.RED
            canvas?.translate(100f, 0f)
            canvas?.drawRect(rectf1, p)
            canvas?.translate(100f, 0f)
            canvas?.drawRect(rectf1, p)
            canvas?.save()
            p.color = Color.BLUE
            canvas?.translate(100f, 0f)
            canvas?.drawRect(rectf1, p)
            canvas?.translate(100f, 0f)
            canvas?.drawRect(rectf1, p)
            if (needSave != null) {
                canvas.restoreToCount(needSave)
                p.color = Color.BLACK
                canvas.drawRect(rectf2, p)
            }
            if (initSave != null) {
                canvas.restoreToCount(initSave)
                p.color = Color.MAGENTA
                canvas.drawRect(rectf2, p)
            }
        }
    }
}