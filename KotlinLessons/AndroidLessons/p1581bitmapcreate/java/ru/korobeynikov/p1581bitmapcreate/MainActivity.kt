package ru.korobeynikov.p1581bitmapcreate

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DrawView(this))
    }

    private class DrawView(context: Context) : View(context) {

        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val bitmap1 = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565)
        private val bitmap2 = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565)
        private val bitmap3 = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565)

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
            paint.style = Paint.Style.STROKE
            val bitmapIcon = drawableToBitmap(resources.getDrawable(R.mipmap.ic_launcher))
            var canvas = Canvas(bitmap1)
            canvas.drawBitmap(bitmapIcon, 0f, 0f, paint)
            canvas = Canvas(bitmap2)
            canvas.drawBitmap(bitmapIcon, 0f, 0f, paint)
            bitmap2.density = DisplayMetrics.DENSITY_XHIGH
            bitmap3.density = DisplayMetrics.DENSITY_XHIGH
            canvas = Canvas(bitmap3)
            canvas.drawBitmap(bitmapIcon, 0f, 0f, paint)
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            canvas?.translate(100f, 100f)
            canvas?.drawRect(0f, 0f, 100f, 100f, paint)
            canvas?.drawBitmap(bitmap1, 0f, 0f, paint)
            canvas?.translate(150f, 0f)
            canvas?.drawRect(0f, 0f, 100f, 100f, paint)
            canvas?.drawBitmap(bitmap2, 0f, 0f, paint)
            canvas?.translate(150f, 0f)
            canvas?.drawRect(0f, 0f, 100f, 100f, paint)
            canvas?.drawBitmap(bitmap3, 0f, 0f, paint)
        }
    }
}