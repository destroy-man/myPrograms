package ru.korobeynikov.p1651shader

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

        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        init {
            paint.shader = createShader()
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            canvas?.drawRect(100f, 100f, 400f, 300f, paint)
            canvas?.drawCircle(300f, 400f, 100f, paint)
        }

        private fun createShader(): Shader {
            return LinearGradient(120f, 0f, 380f, 0f, intArrayOf(Color.RED,
                Color.BLUE, Color.GREEN), floatArrayOf(0f, 0.7f, 1f), Shader.TileMode.REPEAT)
        }
    }
}