package ru.korobeynikov.p1531colorfilter

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

        private val paint = Paint()
        private val rect = Rect(0, 0, 200, 200)
        private val cmData = floatArrayOf(-1f, 0f, 0f, 0f, 255f,
                                          0f, -1f, 0f, 0f, 255f,
                                          0f, 0f, -1f, 0f, 255f,
                                          0f, 0f, 0f, 1f, 0f)
        private val cm = ColorMatrix(cmData)
        private val filter: ColorMatrixColorFilter
        private val icon = bitmapDescriptorFromVector(context, R.mipmap.ic_launcher)

        init {
            paint.style = Paint.Style.FILL_AND_STROKE
            cm.setScale(1f, 1f, 0f, 0.5f)
            filter = ColorMatrixColorFilter(cm)
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            canvas?.translate(100f, 100f)
            drawObjects(canvas)
            paint.colorFilter = filter
            canvas?.translate(0f, 300f)
            drawObjects(canvas)
        }

        private fun drawObjects(canvas: Canvas?) {
            canvas?.save()
            paint.color = Color.RED
            canvas?.drawRect(rect, paint)
            paint.color = Color.GREEN
            canvas?.translate(220f, 0f)
            canvas?.drawRect(rect, paint)
            paint.color = Color.BLUE
            canvas?.translate(220f, 0f)
            canvas?.drawRect(rect, paint)
            paint.color = Color.WHITE
            canvas?.translate(220f, 0f)
            canvas?.drawRect(rect, paint)
            canvas?.translate(220f, 0f)
            if (icon != null)
                canvas?.drawBitmap(icon, null, rect, paint)
            canvas?.restore()
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