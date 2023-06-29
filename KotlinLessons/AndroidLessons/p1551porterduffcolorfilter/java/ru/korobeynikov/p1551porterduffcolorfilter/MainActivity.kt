package ru.korobeynikov.p1551porterduffcolorfilter

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(DrawView(this))
    }

    class DrawView(context: Context) : View(context) {

        private val colorSrc = intArrayOf(Color.rgb(50, 0, 0),
            Color.rgb(100, 0, 0), Color.rgb(150, 0, 0),
            Color.rgb(200, 0, 0), Color.rgb(250, 0, 0))
        private val paints = Array(colorSrc.size) { Paint() }
        private val paintBorder = Paint()
        private val size = 200
        private val mode = PorterDuff.Mode.MULTIPLY
        private var bitmap = bitmapDescriptorFromVector(context, R.mipmap.ic_launcher)

        init {
            setLayerType(LAYER_TYPE_SOFTWARE, null)
            bitmap?.let {
                bitmap = Bitmap.createScaledBitmap(it, size, size, true)
            }
            for (i in colorSrc.indices) {
                val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                paint.colorFilter = PorterDuffColorFilter(colorSrc[i], mode)
                paints[i] = paint
            }
            with(paintBorder) {
                style = Paint.Style.STROKE
                strokeWidth = 3f
                color = Color.BLACK
            }
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.translate(0f, 200f)
            val delta = (width - size * paints.size) / (paints.size + 1f)
            for (i in paints.indices) {
                canvas?.translate(delta, 0f)
                bitmap?.let {
                    canvas?.drawBitmap(it, 0f, 0f, paints[i])
                }
                canvas?.drawRect(0f, 0f, size.toFloat(), size.toFloat(), paintBorder)
                canvas?.translate(size.toFloat(), 0f)
            }
        }

        private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int) =
            ContextCompat.getDrawable(context, vectorResId)?.run {
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
                draw(Canvas(bitmap))
                return@run bitmap
            }
    }
}