package ru.korobeynikov.p1591bitmapoptions

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_PERMISSION_WRITE_STORAGE = 1
    }

    private lateinit var drawView: DrawView

    private fun checkPermissions(permissions: Array<String>) = permissions.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_WRITE_STORAGE -> {
                if (grantResults.isNotEmpty()) {
                    val allPermissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
                    if (allPermissionsGranted)
                        writeFile(drawView.bitmap)
                    else {
                        Toast.makeText(this, "Для продолжения работы с приложением " +
                                "необходимо получить разрешение!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun processPermission() {
        try {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.addCategory("android.intent.category.DEFAULT")
            intent.data = Uri.parse(String.format("package:%s", this.packageName))
            launcherManagerStorage.launch(intent)
        } catch (e: Exception) {
            val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            launcherManagerStorage.launch(intent)
        }
    }

    private val launcherManagerStorage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            writeFile(drawView.bitmap)
        }

    fun writeFile(bitmap: Bitmap) {
        val file = File(Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "savedBitmap.png")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        } finally {
            fos?.close()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        drawView = DrawView(this)
        setContentView(drawView)
    }

    inner class DrawView(context: Context) : View(context) {

        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val bitmap: Bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.RGB_565)

        init {
            paint.textSize = 40f
            var bmpIcon = bitmapDescriptorFromVector(context, R.mipmap.ic_launcher)
            bmpIcon?.let {
                bmpIcon = Bitmap.createScaledBitmap(it, 500, 500, true)
            }
            val canvas = Canvas(bitmap)
            canvas.drawColor(Color.WHITE)
            bmpIcon?.let {
                canvas.drawBitmap(it, 0f, 0f, paint)
            }
            canvas.drawText("Saved bitmap", 100f, 50f, paint)
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager())
                        writeFile(bitmap)
                    else
                        processPermission()
                } else {
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    if (checkPermissions(permissions))
                        writeFile(bitmap)
                    else
                        ActivityCompat.requestPermissions(this@MainActivity, permissions,
                            REQUEST_CODE_PERMISSION_WRITE_STORAGE)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawARGB(80, 102, 204, 255)
            canvas?.drawBitmap(bitmap, 100f, 100f, paint)
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