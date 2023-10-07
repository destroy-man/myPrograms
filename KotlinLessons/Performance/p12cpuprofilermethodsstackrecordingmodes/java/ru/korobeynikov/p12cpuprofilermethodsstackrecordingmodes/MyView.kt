package ru.korobeynikov.p12cpuprofilermethodsstackrecordingmodes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Trace
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
        Trace.beginSection("my lines")
        for (i in 0 until 2000) {
            paint.color = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            canvas.drawLine(rnd.nextInt(width).toFloat(), rnd.nextInt(height).toFloat(),
                rnd.nextInt(width).toFloat(), rnd.nextInt(height).toFloat(), paint)
        }
        Trace.endSection()
        postInvalidate()
    }
}