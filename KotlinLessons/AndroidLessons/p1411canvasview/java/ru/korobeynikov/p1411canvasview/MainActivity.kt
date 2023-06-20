package ru.korobeynikov.p1411canvasview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(DrawView(this))
    }

    class DrawView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

        private lateinit var drawThread: DrawThread

        init {
            holder.addCallback(this)
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

        override fun surfaceCreated(holder: SurfaceHolder) {
            drawThread = DrawThread(holder).apply {
                setRunning(true)
                start()
            }
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            var retry = true
            drawThread.setRunning(false)
            while (retry) {
                try {
                    drawThread.join()
                    retry = false
                } catch (e: InterruptedException) {}
            }
        }

        class DrawThread(private val surfaceHolder: SurfaceHolder) : Thread() {

            private var running = false

            fun setRunning(running: Boolean) {
                this.running = running
            }

            override fun run() {
                while (running) {
                    var canvas: Canvas? = null
                    try {
                        canvas = surfaceHolder.lockCanvas(null)
                        if (canvas == null)
                            continue
                        canvas.drawColor(Color.GREEN)
                    } finally {
                        if (canvas != null)
                            surfaceHolder.unlockCanvasAndPost(canvas)
                    }
                }
            }
        }
    }
}