package ru.korobeynikov.p1331camerarecord

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.SurfaceHolder
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private var camera: Camera? = null
    private var mediaRecorder: MediaRecorder? = null
    private lateinit var photoFile: File
    private lateinit var videoFile: File
    private val REQUEST_CODE_PERMISSION_CAMERA = 1
    private val REQUEST_CODE_PERMISSION_MANAGE_STORAGE = 3

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                try {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.addCategory("android.intent.category.DEFAULT")
                    intent.data = Uri.parse(String.format("package:%s", this.packageName))
                    startActivityForResult(intent, REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
                } catch (e: Exception) {
                    val intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                    startActivityForResult(intent, REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
                }
            } else
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO), REQUEST_CODE_PERMISSION_CAMERA)
        else {
            val pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            photoFile = File(pictures, "myphoto.jpg")
            videoFile = File(pictures, "myvideo.3gp")
            val holder = surfaceView.holder
            holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(p0: SurfaceHolder) {
                    try {
                        camera?.setPreviewDisplay(holder)
                        camera?.startPreview()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

                override fun surfaceDestroyed(p0: SurfaceHolder) {}
            })
        }
    }

    override fun onResume() {
        super.onResume()
        camera = Camera.open()
    }

    override fun onPause() {
        super.onPause()
        releaseMediaRecorder()
        if (camera != null)
            camera?.release()
        camera = null
    }

    fun onClickPicture(view: View) {
        camera?.takePicture(null, null) { data, camera ->
            try {
                val fos = FileOutputStream(photoFile)
                fos.write(data)
                fos.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onClickStartRecord(view: View) {
        if (prepareVideoRecorder())
            mediaRecorder?.start()
        else
            releaseMediaRecorder()
    }

    fun onClickStopRecord(view: View) {
        if (mediaRecorder != null) {
            mediaRecorder?.stop()
            releaseMediaRecorder()
        }
    }

    private fun prepareVideoRecorder(): Boolean {
        camera?.unlock()
        mediaRecorder = MediaRecorder()
        mediaRecorder?.setCamera(camera)
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
        mediaRecorder?.setVideoSource(MediaRecorder.VideoSource.CAMERA)
        mediaRecorder?.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH))
        mediaRecorder?.setOutputFile(videoFile.absolutePath)
        mediaRecorder?.setPreviewDisplay(surfaceView.holder.surface)
        try {
            mediaRecorder?.prepare()
        } catch (e: Exception) {
            e.printStackTrace()
            releaseMediaRecorder()
            return false
        }
        return true
    }

    private fun releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder?.reset()
            mediaRecorder?.release()
            mediaRecorder = null
            camera?.lock()
        }
    }
}