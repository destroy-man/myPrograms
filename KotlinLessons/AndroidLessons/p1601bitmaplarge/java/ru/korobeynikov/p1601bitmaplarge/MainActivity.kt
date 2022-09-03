package ru.korobeynikov.p1601bitmaplarge

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_PERMISSION_READ_STORAGE = 1
    private val REQUEST_CODE_PERMISSION_MANAGE_STORAGE = 2

    private lateinit var mImageView: ImageView

    companion object {

        private fun decodedSampledBitmapFromResource(path: String, reqWidth: Int, reqHeight: Int): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, options)
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFile(path, options)
        }

        private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int,
                                          reqHeight: Int): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {
                val halfHeight = height / 2
                val halfWidth = width / 2
                while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth)
                    inSampleSize *= 2
            }
            return inSampleSize
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_READ_STORAGE -> readImage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mImageView = imageView
        logMemory()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", this.packageName))
                this.startActivityForResult(intent, REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                this.startActivityForResult(intent, REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
            }
        } else
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission
                .READ_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_READ_STORAGE)
    }

    private fun readImage() {
        val px = resources.getDimensionPixelSize(R.dimen.image_size)
        val file = File(Environment.getExternalStoragePublicDirectory(Environment
            .DIRECTORY_DOWNLOADS), "map.jpg")
        val bitmap = decodedSampledBitmapFromResource(file.absolutePath, px, px)
        Log.d("myLogs", String.format("Required size = %s, bitmap size = %sx%s, " +
                "byteCount = %s", px, bitmap.width, bitmap.height, bitmap.byteCount / 1024))
        mImageView.setImageBitmap(bitmap)
        logMemory()
    }

    private fun logMemory() {
        Log.i("myLogs", String.format("Total memory = %s", Runtime.getRuntime().totalMemory() / 1024))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
            readImage()
    }
}