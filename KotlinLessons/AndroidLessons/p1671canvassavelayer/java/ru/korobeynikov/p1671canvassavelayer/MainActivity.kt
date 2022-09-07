package ru.korobeynikov.p1671canvassavelayer

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

        private val mShaderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val mBlackPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        private var mBitmap = BitmapFactory.decodeResource(resources, R.drawable.image)
        private val mRect = Rect(0, 40, 750, 370)
        private val mRectF = RectF(mRect)

        init {
            setLayerType(LAYER_TYPE_SOFTWARE, null)
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mRect.width(), mRect.height(), true)
            mShaderPaint.shader = createShader()
            mShaderPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            mBlackPaint.color = Color.BLACK
            mBlackPaint.alpha = 100
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawBitmap(mBitmap, 0f, 0f, mPaint)
            canvas?.saveLayer(mRectF, mPaint, Canvas.ALL_SAVE_FLAG)
            canvas?.drawRect(mRect, mBlackPaint)
            canvas?.drawOval(mRectF, mShaderPaint)
            canvas?.restore()
        }

        private fun createShader(): Shader {
            val colors = intArrayOf(0xff000000.toInt(), 0xff000000.toInt(), 0)
            val anchors = floatArrayOf(0f, 0.5f, 1f)
            val shader = RadialGradient(0f, 0f, 1f, colors, anchors, Shader.TileMode.CLAMP)
            val matrix = Matrix()
            matrix.postTranslate(mRect.centerX().toFloat(), mRect.centerY().toFloat())
            matrix.postScale(mRect.width() / 2f, mRect.height() / 2f,
                mRect.centerX().toFloat(), mRect.centerY().toFloat())
            shader.setLocalMatrix(matrix)
            return shader
        }
    }
}