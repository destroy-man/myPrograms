package ru.korobeynikov.p1541porterduff

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

        private val paintSrc = Paint()
        private val paintDst = Paint()
        private val paintBorder = Paint()
        private val pathSrc = Path()
        private val pathDst = Path()
        private val bitmapSrc: Bitmap
        private val bitmapDst: Bitmap
        private val mode = PorterDuff.Mode.DST_OUT
        private val colorDst = Color.argb(170, 0, 0, 255)
        private val colorSrc = Color.argb(85, 255, 255, 0)

        init {
            setLayerType(LAYER_TYPE_SOFTWARE, null)
            pathDst.moveTo(0f, 0f)
            pathDst.lineTo(500f, 0f)
            pathDst.lineTo(500f, 500f)
            pathDst.close()
            bitmapDst = createBitmap(pathDst, colorDst)
            pathSrc.moveTo(0f, 0f)
            pathSrc.lineTo(500f, 0f)
            pathSrc.lineTo(0f, 500f)
            pathSrc.close()
            bitmapSrc = createBitmap(pathSrc, colorSrc)
            paintSrc.xfermode = PorterDuffXfermode(mode)
            paintBorder.style = Paint.Style.STROKE
            paintBorder.strokeWidth = 3f
            paintBorder.color = Color.BLACK
        }

        private fun createBitmap(path: Path, color: Int): Bitmap {
            val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
            val bitmapCanvas = Canvas(bitmap)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.style = Paint.Style.FILL_AND_STROKE
            paint.color = color
            bitmapCanvas.drawPath(path, paint)
            return bitmap
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.translate(390f, 80f)
            canvas?.drawBitmap(bitmapDst, 0f, 0f, paintDst)
            canvas?.drawBitmap(bitmapSrc, 0f, 0f, paintSrc)
            canvas?.drawRect(0f, 0f, 500f, 500f, paintBorder)
        }
    }
}