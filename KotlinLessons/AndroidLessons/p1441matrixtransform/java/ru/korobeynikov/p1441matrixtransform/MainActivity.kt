package ru.korobeynikov.p1441matrixtransform

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
        private val figuresMatrix = Matrix()
        private val rectf = RectF(100f, 100f, 200f, 200f)
        private val rectfDst = RectF()
        private val path = Path()

        init {
            p.strokeWidth = 3f
            p.style = Paint.Style.STROKE
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            path.reset()
            path.addRect(rectf, Path.Direction.CW)
            p.color = Color.BLACK
            canvas?.drawPath(path, p)
            figuresMatrix.setRotate(45f, 150f, 150f)
            figuresMatrix.postScale(1.2f, 0.8f, 150f, 150f)
            figuresMatrix.postTranslate(200f, 0f)
            path.transform(figuresMatrix)
            p.color = Color.GREEN
            canvas?.drawPath(path, p)
            figuresMatrix.mapRect(rectfDst, rectf)
            p.color = Color.BLUE
            canvas?.drawRect(rectfDst, p)
        }
    }
}