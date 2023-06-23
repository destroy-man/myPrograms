package ru.korobeynikov.p1471region

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(DrawView(this))
    }

    class DrawView(context: Context) : View(context) {

        private val p = Paint()
        private val path: Path
        private val pathDst: Path
        private val rect = Rect(100, 100, 150, 150)

        init {
            p.strokeWidth = 3f
            p.style = Paint.Style.STROKE
            path = Path().apply {
                moveTo(100f, 100f)
                lineTo(150f, 150f)
                lineTo(100f, 200f)
                close()
            }
            val region = Region()
            region.setPath(path, Region(rect))
            pathDst = region.boundaryPath
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