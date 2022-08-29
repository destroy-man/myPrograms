package ru.korobeynikov.p1471region

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
        private val region = Region()
        private val rect = Rect(100, 100, 150, 150)
        private val clipRegion = Region(rect)
        private val path = Path()
        private val pathDst: Path

        init {
            p.strokeWidth = 3f
            p.style = Paint.Style.STROKE
            path.moveTo(100f, 100f)
            path.lineTo(150f, 150f)
            path.lineTo(100f, 200f)
            path.close()
            region.setPath(path, clipRegion)
            pathDst = region.boundaryPath
            pathDst.close()
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            p.color = Color.GREEN
            canvas?.drawPath(path, p)
            canvas?.translate(200f, 0f)
            p.color = Color.BLUE
            canvas?.drawPath(pathDst, p)
        }
    }
}