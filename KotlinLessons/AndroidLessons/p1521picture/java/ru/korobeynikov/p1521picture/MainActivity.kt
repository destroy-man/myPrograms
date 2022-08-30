package ru.korobeynikov.p1521picture

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

        private val p = Paint(Paint.ANTI_ALIAS_FLAG)
        private val path = Path()
        private val picture = Picture()
        private val rect = Rect(0, 0, 100, 100)
        private val rect1 = Rect(0, 0, 500, 200)

        init {
            val canvas = picture.beginRecording(300, 300)
            p.color = Color.GREEN
            canvas.drawCircle(150f, 100f, 80f, p)
            p.color = Color.BLUE
            canvas.drawRect(20f, 70f, 150f, 200f, p)
            p.color = Color.RED
            path.moveTo(170f, 80f)
            path.lineTo(240f, 210f)
            path.lineTo(100f, 210f)
            path.close()
            canvas.drawPath(path, p)
            picture.endRecording()
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            canvas?.drawPicture(picture)
            canvas?.translate(300f, 0f)
            canvas?.drawPicture(picture, rect)
            canvas?.translate(0f, 300f)
            canvas?.drawPicture(picture, rect1)
        }
    }
}