package ru.korobeynikov.p1511patheffect

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

        private val path = Path()
        private val p1 = Paint(Paint.ANTI_ALIAS_FLAG)
        private val p2: Paint
        private val p3: Paint
        private val p4: Paint
        private val p5: Paint

        init {
            path.addRect(-100f, 0f, 100f, 500f, Path.Direction.CW)
            val pe1 = CornerPathEffect(100f)
            val pe2 = DiscretePathEffect(15f, 10f)
            p1.style = Paint.Style.STROKE
            p1.strokeWidth = 7f
            p2 = Paint(p1)
            p2.color = Color.GREEN
            p2.pathEffect = pe1
            p3 = Paint(p1)
            p3.color = Color.BLUE
            p3.pathEffect = pe2
            p4 = Paint(p1)
            p4.color = Color.RED
            p4.pathEffect = ComposePathEffect(pe1, pe2)
            p5 = Paint(p1)
            p5.color = Color.YELLOW
            p5.pathEffect = ComposePathEffect(pe2, pe1)
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.let {
                with(it) {
                    drawARGB(80, 102, 204, 255)
                    translate(120f, 100f)
                    drawPath(path, p1)
                    translate(250f, 0f)
                    drawPath(path, p2)
                    translate(250f, 0f)
                    drawPath(path, p3)
                    translate(250f, 0f)
                    drawPath(path, p4)
                    translate(250f, 0f)
                    drawPath(path, p5)
                }
            }
        }
    }
}