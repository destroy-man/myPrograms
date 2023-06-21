package ru.korobeynikov.p1431drawingpath

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
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
        private val path = Path()
        private val text = "Draw the text, with origin at (x,y), using specified paint"

        init {
            p.strokeWidth = 1f
            p.textSize = 20f
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            //Черный текст
            path.reset()
            path.addCircle(200f, 200f, 100f, Path.Direction.CW)
            p.color = Color.BLACK
            canvas?.drawTextOnPath(text, path, 0f, 0f, p)
            //Синий текст
            path.reset()
            path.addCircle(500f, 200f, 100f, Path.Direction.CCW)
            p.style = Paint.Style.FILL
            p.color = Color.BLUE
            canvas?.drawTextOnPath(text, path, 0f, 0f, p)
            p.style = Paint.Style.STROKE
            canvas?.drawPath(path, p)
            //Зеленый текст
            path.offset(-300f, 250f)
            p.style = Paint.Style.FILL
            p.color = Color.GREEN
            canvas?.drawTextOnPath(text, path, 100f, 0f, p)
            p.style = Paint.Style.STROKE
            canvas?.drawPath(path, p)
            //Красный текст
            path.offset(300f, 0f)
            p.style = Paint.Style.FILL
            p.color = Color.RED
            canvas?.drawTextOnPath(text, path, 0f, 30f, p)
            p.style = Paint.Style.STROKE
            canvas?.drawPath(path, p)
        }
    }
}