package ru.korobeynikov.p1551porterduffcolorfilter

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DrawView(this))
    }

    private class DrawView(context: Context) : View(context) {

        private val colorSrc = intArrayOf(Color.rgb(50, 0, 0),
            Color.rgb(100, 0, 0), Color.rgb(150, 0, 0),
            Color.rgb(200, 0, 0), Color.rgb(250, 0, 0))
        private val paints = Array<Paint?>(colorSrc.size) { null }
        private val paintBorder = Paint()
        private var bitmap = drawableToBitmap(resources.getDrawable(R.mipmap.ic_launcher))
        private val size = 200
        private val mode = PorterDuff.Mode.MULTIPLY

        private fun drawableToBitmap(drawable: Drawable): Bitmap {
            if (drawable is BitmapDrawable)
                return drawable.bitmap
            val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }

        init {
            setLayerType(LAYER_TYPE_SOFTWARE, null)
            bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true)
            for (i in colorSrc.indices) {
                val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                paint.colorFilter = PorterDuffColorFilter(colorSrc[i], mode)
                paints[i] = paint
            }
            paintBorder.style = Paint.Style.STROKE
            paintBorder.strokeWidth = 3f
            paintBorder.color = Color.BLACK
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.translate(0f, 200f)
            val delta = (width - size * paints.size) / (paints.size + 1)
            for (i in paints.indices) {
                canvas?.translate(delta.toFloat(), 0f)
                canvas?.drawBitmap(bitmap, 0f, 0f, paints[i])
                canvas?.drawRect(0f, 0f, size.toFloat(), size.toFloat(), paintBorder)
                canvas?.translate(size.toFloat(), 0f)
            }
        }
    }
}