package ru.korobeynikov.p1321camerascreen

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.graphics.RectF
import android.hardware.Camera
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var sv: SurfaceView
    lateinit var holder: SurfaceHolder
    lateinit var holderCallback: HolderCallback
    var camera: Camera? = null
    val CAMERA_ID = 0
    private val FULL_SCREEN = true
    private val REQUEST_CODE_PERMISSION_CAMERA = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)

        if (android.os.Build.VERSION.SDK_INT >= 23)
            if (this.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                this.requestPermissions(arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CODE_PERMISSION_CAMERA)
        sv = surfaceView
        holder = sv.holder
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
        holderCallback = HolderCallback()
        holder.addCallback(holderCallback)
    }

    override fun onResume() {
        super.onResume()
        camera = Camera.open(CAMERA_ID)
        setPreviewSize(FULL_SCREEN)
    }

    override fun onPause() {
        super.onPause()
        if (camera != null)
            camera?.release()
        camera = null
    }

    inner class HolderCallback : SurfaceHolder.Callback {

        override fun surfaceCreated(holder: SurfaceHolder) {
            try {
                camera?.setPreviewDisplay(holder)
                camera?.startPreview()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
            camera?.stopPreview()
            setCameraDisplayOrientation(CAMERA_ID)
            try {
                camera?.setPreviewDisplay(holder)
                camera?.startPreview()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun surfaceDestroyed(p0: SurfaceHolder) {}
    }

    private fun setPreviewSize(fullScreen: Boolean) {
        val display = windowManager.defaultDisplay
        val widthIsMax = display.width > display.height
        if (camera != null) {
            val size = camera!!.parameters.previewSize
            val rectDisplay = RectF()
            val rectPreview = RectF()
            rectDisplay.set(0f, 0f, display.width.toFloat(), display.height.toFloat())
            if (widthIsMax)
                rectPreview.set(0f, 0f, size.width.toFloat(), size.height.toFloat())
            else
                rectPreview.set(0f, 0f, size.height.toFloat(), size.width.toFloat())
            val matrix = Matrix()
            if (!fullScreen)
                matrix.setRectToRect(rectPreview, rectDisplay, Matrix.ScaleToFit.START)
            else {
                matrix.setRectToRect(rectDisplay, rectPreview, Matrix.ScaleToFit.START)
                matrix.invert(matrix)
            }
            matrix.mapRect(rectPreview)
            sv.layoutParams.height = rectPreview.bottom.toInt()
            sv.layoutParams.width = rectPreview.right.toInt()
        }
    }

    fun setCameraDisplayOrientation(cameraId: Int) {
        val rotation = windowManager.defaultDisplay.rotation
        var degress = 0
        when (rotation) {
            Surface.ROTATION_0 -> degress = 0
            Surface.ROTATION_90 -> degress = 90
            Surface.ROTATION_180 -> degress = 180
            Surface.ROTATION_270 -> degress = 270
        }
        var result = 0
        val info = Camera.CameraInfo()
        Camera.getCameraInfo(cameraId, info)
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK)
            result = (360 - degress + info.orientation)
        else if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (360 - degress - info.orientation)
            result += 360
        }
        result %= 360
        camera?.setDisplayOrientation(result)
    }
}