package ru.korobeynikov.p1291mediarecorderaudio

import android.Manifest
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.view.View
import java.io.File

class MainActivity : AppCompatActivity() {

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var fileName: String

    private val REQUEST_CODE_PERMISSION_RECORD_AUDIO = 1
    private val REQUEST_CODE_PERMISSION_MANAGE_STORAGE = 2

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_RECORD_AUDIO -> recordAudio()
        }
    }

    private fun recordAudio() {
        try {
            releaseRecorder()
            val outFile = File(fileName)
            if (outFile.exists())
                outFile.delete()
            mediaRecorder = MediaRecorder()
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder!!.setOutputFile(fileName)
            mediaRecorder!!.prepare()
            mediaRecorder!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fileName = "${Environment.getExternalStorageDirectory()}/record.3gpp"
    }

    fun recordStart(v: View) {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_RECORD_AUDIO)
    }

    fun recordStop(v: View) {
        if (mediaRecorder != null)
            mediaRecorder!!.stop()
    }

    fun playStart(v: View) {
        try {
            releasePlayer()
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setDataSource(fileName)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playStop(v: View) {
        if (mediaPlayer != null)
            mediaPlayer!!.stop()
    }

    private fun releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder!!.release()
            mediaRecorder = null
        }
    }

    private fun releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
        releaseRecorder()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
            recordAudio()
    }
}