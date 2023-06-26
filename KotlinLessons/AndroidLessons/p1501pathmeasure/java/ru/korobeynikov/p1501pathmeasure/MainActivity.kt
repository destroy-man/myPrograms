package ru.korobeynikov.p1501pathmeasure

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(DrawView(this))
    }

    class DrawView(context: Context) : View(context) {

        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val path: Path
        private val path1: Path
        private val pMeasure: PathMeasure

        init {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 3f
            path = Path().apply {
                moveTo(100f, 300f)
                rLineTo(150f, 150f)
                rLineTo(150f, -100f)
                rQuadTo(150f, 200f, 300f, 0f)
                rLineTo(150f, 100f)
                rLineTo(150f, -150f)
            }
            pMeasure = PathMeasure(path, false)
            path1 = Path()
            pMeasure.getSegment(150f, 850f, path1, true)
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            canvas?.drawPath(path1, paint)
        }
    }
}