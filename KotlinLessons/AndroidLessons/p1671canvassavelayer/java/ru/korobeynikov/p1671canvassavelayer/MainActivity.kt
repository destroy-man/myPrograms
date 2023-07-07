package ru.korobeynikov.p1671canvassavelayer

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

        private val mShaderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val mBlackPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val mRect = Rect(0, 40, 750, 370)
        private val mRectF = RectF(mRect)
        private var mBitmap = BitmapFactory.decodeResource(resources, R.drawable.image)

        init {
            setLayerType(LAYER_TYPE_SOFTWARE, null)
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mRect.width(), mRect.height(), true)
            mShaderPaint.shader = createShader()
            mShaderPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            mBlackPaint.color = Color.BLACK
            mBlackPaint.alpha = 100
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.let {
                with(it) {
                    drawBitmap(mBitmap, 0f, 0f, mPaint)
                    saveLayer(mRectF, mPaint)
                    drawRect(mRect, mBlackPaint)
                    drawOval(mRectF, mShaderPaint)
                    restore()
                }
            }
        }

        private fun createShader(): Shader {
            val colors = intArrayOf(0xff000000.toInt(), 0xff000000.toInt(), 0)
            val anchors = floatArrayOf(0f, 0.5f, 1f)
            val shader = RadialGradient(0f, 0f, 1f, colors, anchors, Shader.TileMode.CLAMP)
            val matrix = Matrix()
            matrix.postTranslate(mRectF.centerX(), mRectF.centerY())
            matrix.postScale(mRectF.width() / 2, mRectF.height() / 2, mRectF.centerX(),
                mRectF.centerY())
            shader.setLocalMatrix(matrix)
            return shader
        }
    }
}