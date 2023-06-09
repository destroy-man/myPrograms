package ru.korobeynikov.p1341camerafeatures

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Surface.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.*
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1341camerafeatures.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "myLogs"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).toTypedArray()
    }

    private var imageCapture: ImageCapture? = null
    private val listFlashModes = arrayOf(FLASH_MODE_AUTO, FLASH_MODE_ON, FLASH_MODE_OFF)
    private val listRotationAngles = arrayOf(ROTATION_0, ROTATION_90, ROTATION_180, ROTATION_270)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.view = this
        initSpinner(arrayOf("Авто", "Включить", "Выключить"), binding.spFlashModes)
        initSpinner(arrayOf("0", "90", "180", "270"), binding.spRotation)
        if (allPermissionsGranted())
            startCamera()
        else
            requestPermissions()
    }

    private fun initSpinner(data: Array<String>, spinner: Spinner) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun takePhoto() {
        val imageCapture = imageCapture ?: return
        imageCapture.flashMode = listFlashModes[binding.spFlashModes.selectedItemPosition]
        imageCapture.targetRotation = listRotationAngles[binding.spRotation.selectedItemPosition]
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.getDefault()).format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P)
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Camera image")
        }
        val outputOptions = OutputFileOptions
            .Builder(contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues).build()
        imageCapture.takePicture(outputOptions, ActivityCompat.getMainExecutor(this),
            object : OnImageSavedCallback {

                override fun onError(exc: ImageCaptureException) {
                    Log.d(TAG, "Не удалось сделать фото: ${exc.message}", exc)
                }

                override fun onImageSaved(output: OutputFileResults) {
                    Toast.makeText(this@MainActivity,
                        "Фото сделано: ${output.savedUri}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }
            imageCapture = Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                Log.d(TAG, "Не удалось отобразить изображение с камеры", exc)
            }
        }, ActivityCompat.getMainExecutor(this))
    }

    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value)
                    permissionGranted = false
            }
            if (!permissionGranted)
                Toast.makeText(this, "Разрешения не получены", Toast.LENGTH_SHORT).show()
            else
                startCamera()
        }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
}