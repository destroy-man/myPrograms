package ru.korobeynikov.p1311cameraintent

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var directory: File
    private val TYPE_PHOTO = 1
    private val TYPE_VIDEO = 2
    private val REQUEST_CODE_PHOTO = 1
    private val REQUEST_CODE_VIDEO = 2
    private val TAG = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createDirectory()
    }

    fun onClickPhoto(view: View) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_PHOTO))
        startActivityForResult(intent, REQUEST_CODE_PHOTO)
    }

    fun onClickVideo(view: View) {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_VIDEO))
        startActivityForResult(intent, REQUEST_CODE_VIDEO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent == null)
                    Log.d(TAG, "Intent is null")
                else {
                    Log.d(TAG, "Photo uri: ${intent.data}")
                    val bndl = intent.extras
                    if (bndl != null) {
                        val obj = intent.extras?.get("data")
                        if (obj is Bitmap) {
                            Log.d(TAG, "bitmap ${obj.width} x ${obj.height}")
                            ivPhoto.setImageBitmap(obj)
                        }
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Log.d(TAG, "Canceled")
        }
        if (requestCode == REQUEST_CODE_VIDEO) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent == null)
                    Log.d(TAG, "Intent is null")
                else
                    Log.d(TAG, "Video uri: ${intent.data}")
            } else if (resultCode == Activity.RESULT_CANCELED)
                Log.d(TAG, "Canceled")
        }
    }

    private fun generateFileUri(type: Int): Uri {
        var file: File? = null
        when (type) {
            TYPE_PHOTO -> file = File("${directory.path}/photo_${System.currentTimeMillis()}.jpg")
            TYPE_VIDEO -> file = File("${directory.path}/video_${System.currentTimeMillis()}.mp4")
        }
        Log.d(TAG, "fileName = $file")
        return FileProvider.getUriForFile(this,
            "${this.applicationContext.packageName}.provider", file!!)
    }

    private fun createDirectory() {
        directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "MyFolder")
        if (!directory.exists())
            directory.mkdirs()
    }
}