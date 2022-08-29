package ru.korobeynikov.p1501pathmeasure

import android.content.Context
import android.graphics.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams
            .FLAG_FULLSCREEN)
        setContentView(DrawView(this))
    }

    private inner class DrawView(context: Context) : View(context) {

        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val path: Path
        private val pMeasure: PathMeasure
        private val path1 = Path()

        init {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 3f
            path = Path()
            path.moveTo(100f, 300f)
            path.rLineTo(150f, 150f)
            path.rLineTo(150f, -100f)
            path.rQuadTo(150f, 200f, 300f, 0f)
            path.rLineTo(150f, 100f)
            path.rLineTo(150f, -150f)
            pMeasure = PathMeasure(path, false)
            pMeasure.getSegment(150f, 850f, path1, true)
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            canvas?.drawPath(path1, paint)
        }
    }
}