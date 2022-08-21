package ru.korobeynikov.p1411canvasview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DrawView(this))
    }

    private class DrawView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

        private lateinit var drawThread: DrawThread

        init {
            holder.addCallback(this)
        }

        override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

        override fun surfaceCreated(p0: SurfaceHolder) {
            drawThread = DrawThread(holder)
            drawThread.setRunning(true)
            drawThread.start()
        }

        override fun surfaceDestroyed(p0: SurfaceHolder) {
            var retry = true
            drawThread.setRunning(false)
            while (retry)
                try {
                    drawThread.join()
                    retry = false
                } catch (e: InterruptedException) { }
        }

        private class DrawThread(private val surfaceHolder: SurfaceHolder) : Thread() {

            private var running = false

            fun setRunning(running: Boolean) {
                this.running = running
            }

            override fun run() {
                var canvas: Canvas?
                while (running) {
                    canvas = null
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