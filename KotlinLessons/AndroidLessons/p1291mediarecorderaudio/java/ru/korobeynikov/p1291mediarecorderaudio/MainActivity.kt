package ru.korobeynikov.p1291mediarecorderaudio

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1291mediarecorderaudio.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_PERMISSION_RECORD_AUDIO = 1
        const val REQUEST_CODE_PERMISSION_WRITE_STORAGE = 2
    }

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var fileName: String

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        writeFileMusic()
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
            writeFileMusic()
        }

    private fun writeFileMusic() {
        try {
            releaseRecorder()
            val outFile = File(fileName)
            if (outFile.exists())
                outFile.delete()
            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                MediaRecorder(this)
            else
                MediaRecorder()
            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder?.setOutputFile(outFile)
            mediaRecorder?.prepare()
            mediaRecorder?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        fileName = "${Environment.getExternalStorageDirectory()}/record.3gpp"
    }

    fun recordStart() {
        val statusPermission =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        if (statusPermission == PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                processPermission()
            else
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CODE_PERMISSION_WRITE_STORAGE)
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_CODE_PERMISSION_RECORD_AUDIO)
            recordStart()
        }
    }

    fun recordStop() {
        mediaRecorder?.stop()
    }

    fun playStart() {
        try {
            releasePlayer()
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(fileName)
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playStop() {
        mediaPlayer?.stop()
    }

    private fun releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder?.release()
            mediaRecorder = null
        }
    }

    private fun releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
        releaseRecorder()
    }
}