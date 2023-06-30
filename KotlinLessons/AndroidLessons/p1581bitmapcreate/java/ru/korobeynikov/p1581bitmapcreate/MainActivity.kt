package ru.korobeynikov.p1581bitmapcreate

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(DrawView(this))
    }

    class DrawView(context: Context) : View(context) {

        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val bitmap1 = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565)
        private val bitmap2 = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565)
        private val bitmap3 = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565)

        init {
            paint.style = Paint.Style.STROKE
            val bitmapIcon = bitmapDescriptorFromVector(context, R.mipmap.ic_launcher)
            var canvas = Canvas(bitmap1)
            if (bitmapIcon != null)
                canvas.drawBitmap(bitmapIcon, 0f, 0f, paint)
            canvas = Canvas(bitmap2)
            if (bitmapIcon != null)
                canvas.drawBitmap(bitmapIcon, 0f, 0f, paint)
            bitmap2.density = DisplayMetrics.DENSITY_XHIGH
            bitmap3.density = DisplayMetrics.DENSITY_XHIGH
            canvas = Canvas(bitmap3)
            if (bitmapIcon != null)
                canvas.drawBitmap(bitmapIcon, 0f, 0f, paint)
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.let {
                with(it) {
                    drawARGB(80, 102, 204, 255)
                    translate(100f, 100f)
                    drawRect(0f, 0f, 100f, 100f, paint)
                    drawBitmap(bitmap1, 0f, 0f, paint)
                    translate(150f, 0f)
                    drawRect(0f, 0f, 100f, 100f, paint)
                    drawBitmap(bitmap2, 0f, 0f, paint)
                    translate(150f, 0f)
                    drawRect(0f, 0f, 100f, 100f, paint)
                    drawBitmap(bitmap3, 0f, 0f, paint)
                }
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