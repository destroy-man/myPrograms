package ru.korobeynikov.p1441matrixtransform

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
        private val matrixDraw = Matrix()
        private val rectf = RectF(100f, 100f, 200f, 200f)
        private val rectfDst = RectF()
        private val path = Path()

        init {
            p.strokeWidth = 3f
            p.style = Paint.Style.STROKE
            path.addRect(100f, 100f, 200f, 200f, Path.Direction.CW)
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            path.reset()
            path.addRect(rectf, Path.Direction.CW)
            p.color = Color.BLACK
            canvas?.drawPath(path, p)
            matrixDraw.setRotate(45f, 150f, 150f)
            matrixDraw.postScale(1.2f, 0.8f, 150f, 150f)
            matrixDraw.postTranslate(200f, 0f)
            path.transform(matrixDraw)
            p.color = Color.GREEN
            canvas?.drawPath(path, p)
            matrixDraw.mapRect(rectfDst, rectf)
            p.color = Color.BLUE
            canvas?.drawRect(rectfDst, p)
        }
    }
}