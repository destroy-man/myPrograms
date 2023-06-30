package ru.korobeynikov.p1571bitmapread

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(DrawView(this))
    }

    class DrawView(context: Context) : View(context) {

        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val bitmap = bitmapDescriptorFromVector(context, R.mipmap.ic_launcher)
        private val rectSrc =
            Rect(0, 0, (bitmap?.width ?: 0) / 2, (bitmap?.height ?: 0) / 2)
        private val rectDst = Rect(300, 100, 500, 200)
        private val canvasMatrix = Matrix()

        init {
            val info = String.format("Info: size = %s x %s, bytes = %s (%s), config = %s",
                bitmap?.width, bitmap?.height, bitmap?.byteCount, bitmap?.rowBytes, bitmap?.config)
            Log.d("myLogs", info)
            with(canvasMatrix) {
                postRotate(45f)
                postScale(2f, 3f)
                postTranslate(200f, 50f)
            }
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.let {
                with(it) {
                    drawARGB(80, 102, 204, 255)
                    if (bitmap != null) {
                        drawBitmap(bitmap, 50f, 50f, paint)
                        drawBitmap(bitmap, canvasMatrix, paint)
                        drawBitmap(bitmap, rectSrc, rectDst, paint)
                    }
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