package ru.korobeynikov.p1661createowndrawable

import android.graphics.*
import android.graphics.drawable.Drawable

open class HexagonDrawable : Drawable() {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPath = Path()

    override fun draw(canvas: Canvas) {
        canvas.drawPath(mPath, mPaint)
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity() = android.graphics.PixelFormat.TRANSLUCENT

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        val width = bounds?.width()?.toFloat()
        val height = bounds?.height()?.toFloat()
        if (width != null && height != null) {
            with(mPath) {
                reset()
                moveTo(0f, height / 2)
                lineTo(width / 4, 0f)
                lineTo(width * 3 / 4, 0f)
                lineTo(width, height / 2)
                lineTo(width * 3 / 4, height)
                lineTo(width / 4, height)
                close()
            }
        }
    }

    fun getPaint() = mPaint
}