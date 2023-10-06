package ru.korobeynikov.p10cpuprofiler

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*

class MyView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    private val rnd = Random()
    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until 10000) {
            paint.color = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            canvas.drawLine(rnd.nextInt(width).toFloat(), rnd.nextInt(height).toFloat(),
                rnd.nextInt(width).toFloat(), rnd.nextInt(height).toFloat(), paint)
        }
        postInvalidate()
    }
}