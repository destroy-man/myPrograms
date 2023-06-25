package ru.korobeynikov.p1491canvastext

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DrawView(this))
    }

    class DrawView(context: Context) : View(context) {

        private val p = Paint(Paint.ANTI_ALIAS_FLAG)
        private val text = "Test width text"
        private val fontSize = 60
        private val yTranslate = 80f

        init {
            p.textSize = fontSize.toFloat()
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            //Обычный текст
            canvas?.translate(50f, yTranslate)
            canvas?.drawText(text, 0f, 0f, p)
            //Растянутый текст
            canvas?.translate(0f, yTranslate)
            p.textScaleX = 1.5f
            canvas?.drawText(text, 0f, 0f, p)
            p.textScaleX = 1f
            //Наклоненный текст
            canvas?.translate(0f, yTranslate)
            p.textSkewX = 0.5f
            canvas?.drawText(text, 0f, 0f, p)
            p.textSkewX = 0f
            //Подчеркнутый текст
            canvas?.translate(0f, yTranslate)
            p.isUnderlineText = true
            canvas?.drawText(text, 0f, 0f, p)
            p.isUnderlineText = false
            //Зачеркнутый текст
            canvas?.translate(0f, yTranslate)
            p.isStrikeThruText = true
            canvas?.drawText(text, 0f, 0f, p)
            p.isStrikeThruText = false
        }
    }
}