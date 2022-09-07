package ru.korobeynikov.p1661createdrawable

import android.graphics.*
import android.graphics.drawable.Drawable

open class HexagonDrawable : Drawable() {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPath = Path()

    override fun draw(canvas: Canvas) {
        canvas.drawPath(mPath, mPaint)
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        val width = bounds?.width()
        val height = bounds?.height()
        if (width != null && height != null) {
            mPath.reset()
            mPath.moveTo(0f, height / 2f)
            mPath.lineTo(width / 4f, 0f)
            mPath.lineTo(width * 3f / 4f, 0f)
            mPath.lineTo(width.toFloat(), height / 2f)
            mPath.lineTo(width * 3f / 4f, height.toFloat())
            mPath.lineTo(width / 4f, height.toFloat())
            mPath.close()
        }
    }

    protected fun getPaint(): Paint {
        return mPaint
    }
}