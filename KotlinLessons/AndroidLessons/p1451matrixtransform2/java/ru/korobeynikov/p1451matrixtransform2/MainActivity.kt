package ru.korobeynikov.p1451matrixtransform2

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
        private val pBlack = Paint()
        private val pGray = Paint()
        private val pWhite = Paint()
        private val path = Path()
        private val pathDst = Path()
        private val rectf = RectF(100f, 100f, 200f, 200f)
        private val figuresMatrix = Matrix()
        private val src = floatArrayOf(100f, 100f, 200f, 200f, 200f, 100f, 100f, 200f)
        private val dst = floatArrayOf(50f, 300f, 250f, 500f, 230f, 350f, 40f, 550f)
        private val dst2 = floatArrayOf(400f, 200f, 500f, 200f, 440f, 100f, 440f, 230f)
        private val points = 4

        init {
            p.strokeWidth = 3f
            p.style = Paint.Style.STROKE
            pGray.color = Color.GRAY
            pGray.strokeWidth = 3f
            pBlack.color = Color.BLACK
            pBlack.strokeWidth = 3f
            pWhite.color = Color.WHITE
            pWhite.strokeWidth = 3f
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            path.reset()
            path.addRect(rectf, Path.Direction.CW)
            p.color = Color.GREEN
            canvas?.drawPath(path, p)
            canvas?.drawLine(src[0], src[1], src[2], src[3], pBlack)
            canvas?.drawLine(src[0], src[1], src[4], src[5], pGray)
            canvas?.drawLine(src[0], src[1], src[6], src[7], pWhite)
            figuresMatrix.setPolyToPoly(src, 0, dst, 0, points)
            path.transform(figuresMatrix, pathDst)
            p.color = Color.BLUE
            canvas?.drawPath(pathDst, p)
            canvas?.drawLine(dst[0], dst[1], dst[2], dst[3], pBlack)
            canvas?.drawLine(dst[0], dst[1], dst[4], dst[5], pGray)
            canvas?.drawLine(dst[0], dst[1], dst[6], dst[7], pWhite)
            figuresMatrix.setPolyToPoly(src, 0, dst2, 0, points)
            path.transform(figuresMatrix, pathDst)
            p.color = Color.RED
            canvas?.drawPath(pathDst, p)
            canvas?.drawLine(dst2[0], dst2[1], dst2[2], dst2[3], pBlack)
            canvas?.drawLine(dst2[0], dst2[1], dst2[4], dst2[5], pGray)
            canvas?.drawLine(dst2[0], dst2[1], dst2[6], dst2[7], pWhite)
        }
    }
}