package ru.korobeynikov.p1571bitmapread

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DrawView(this))
    }

    private class DrawView(context: Context) : View(context) {

        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val bitmap = drawableToBitmap(resources.getDrawable(R.mipmap.ic_launcher))
        private val rectSrc = Rect(0, 0, bitmap.width / 2, bitmap.height / 2)
        private val rectDst = Rect(300, 100, 500, 200)
        private val matrixDraw = Matrix()

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
            val info = String.format("Info: size = %s x %s, bytes = %s (%s), config = %s", bitmap
                .width, bitmap.height, bitmap.byteCount, bitmap.rowBytes, bitmap.config)
            Log.d("myLogs", info)
            matrixDraw.postRotate(45f)
            matrixDraw.postScale(2f, 3f)
            matrixDraw.postTranslate(200f, 50f)
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            canvas?.drawBitmap(bitmap, 50f, 50f, paint)
            canvas?.drawBitmap(bitmap, matrixDraw, paint)
            canvas?.drawBitmap(bitmap, rectSrc, rectDst, paint)
        }
    }
}