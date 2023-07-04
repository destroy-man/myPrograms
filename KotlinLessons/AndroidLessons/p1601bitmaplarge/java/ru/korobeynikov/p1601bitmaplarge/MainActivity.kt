package ru.korobeynikov.p1601bitmaplarge

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1601bitmaplarge.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {

        const val REQUEST_CODE_PERMISSION_READ_STORAGE = 1

        fun decodeSampledBitmapFromResource(path: String, reqWidth: Int, reqHeight: Int): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, options)
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
            options.inJustDecodeBounds = false
            options.inPreferredConfig = Bitmap.Config.RGB_565
            return BitmapFactory.decodeFile(path, options)
        }

        private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int,
                                          reqHeight: Int): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth)
                while ((height / 2 / inSampleSize) > reqHeight && (width / 2 / inSampleSize) > reqWidth)
                    inSampleSize *= 2
            return inSampleSize
        }
    }

    private lateinit var mImageView: ImageView

    private fun checkPermissions(permissions: Array<String>) = permissions.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_READ_STORAGE -> {
                if (grantResults.isNotEmpty()) {
                    val allPermissionsGranted =
                        grantResults.all { it == PackageManager.PERMISSION_GRANTED }
                    if (allPermissionsGranted) {
                        logMemory()
                        readImage()
                        logMemory()
                    } else {
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
            logMemory()
            readImage()
            logMemory()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        mImageView = binding.imageView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                logMemory()
                readImage()
                logMemory()
            } else
                processPermission()
        } else {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            if (checkPermissions(permissions)) {
                logMemory()
                readImage()
                logMemory()
            } else
                ActivityCompat.requestPermissions(this@MainActivity, permissions,
                    REQUEST_CODE_PERMISSION_READ_STORAGE)
        }
    }

    private fun readImage() {
        val px = resources.getDimensionPixelSize(R.dimen.image_size)
        val file = File(Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "map.jpg")
        val bitmap = decodeSampledBitmapFromResource(file.absolutePath, px, px)
        Log.d("myLogs", String.format("Required size = %s, bitmap size = %sx%s, " +
                "byteCount = %s", px, bitmap.width, bitmap.height, bitmap.byteCount / 1024))
        mImageView.setImageBitmap(bitmap)
    }

    private fun logMemory() {
        Log.i("myLogs",
            String.format("Total memory = %s", Runtime.getRuntime().totalMemory().toInt() / 1024))
    }
}