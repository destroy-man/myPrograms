package ru.korobeynikov.p1311cameraintent

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1311cameraintent.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_PERMISSION_WRITE_STORAGE = 1
    }

    private val requestCodePhoto = 1
    private val requestCodeVideo = 2
    private val tag = "myLogs"
    private var requestCode = 0
    private var fileUri: Uri? = null
    private lateinit var directory: File
    private lateinit var ivPhoto: ImageView

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        writeFile()
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
            writeFile()
        }

    private fun writeFile() {
        createDirectory()
        generateFileUri(requestCode)
        val intent = if (requestCode == requestCodePhoto)
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        else
            Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startForResult.launch(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        ivPhoto = binding.ivPhoto
    }

    fun clickPhoto() {
        requestCode = requestCodePhoto
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            processPermission()
        else
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_WRITE_STORAGE)
    }

    fun clickVideo() {
        requestCode = requestCodeVideo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            processPermission()
        else
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_WRITE_STORAGE)
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data
                if (intent == null)
                    Log.d(tag, "Intent is null")
                else if (requestCode == requestCodePhoto) {
                    Log.d(tag, "Photo uri: ${intent.data}")
                    val bndl = intent.extras
                    if (bndl != null) {
                        val obj = bndl.get("data")
                        if (obj is Bitmap) {
                            Log.d(tag, "bitmap ${obj.width} x ${obj.height}")
                            ivPhoto.setImageBitmap(obj)
                        }
                    }
                } else if (requestCode == requestCodeVideo)
                    Log.d(tag, "Video uri: ${intent.data}")
            } else if (result.resultCode == RESULT_CANCELED)
                Log.d(tag, "Canceled")
        }

    private fun generateFileUri(typeRequest: Int) {
        var file: File? = null
        when (typeRequest) {
            requestCodePhoto -> file =
                File("${directory.path}/photo_${System.currentTimeMillis()}.jpg")
            requestCodeVideo -> file =
                File("${directory.path}/video_${System.currentTimeMillis()}.mp4")
        }
        Log.d(tag, "filename = $file")
        file?.let {
            fileUri = FileProvider
                .getUriForFile(this, "${applicationContext.packageName}.provider", it)
        }
    }

    private fun createDirectory() {
        directory = File(Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyFolder")
        if (!directory.exists())
            directory.mkdirs()
    }
}