package ru.korobeynikov.p1301audiorecorder

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1301audiorecorder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_PERMISSION_RECORD_AUDIO = 1
    }

    private var myBufferSize = 8192
    private var audioRecord: AudioRecord? = null
    val tag = "myLogs"
    var isReading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        createAudioRecorder()
        Log.d(tag, "init state = ${audioRecord?.state}")
    }

    private fun createAudioRecorder() {
        val sampleRate = 8000
        val channelConfig = AudioFormat.CHANNEL_IN_MONO
        val audioFormat = AudioFormat.ENCODING_PCM_16BIT
        val minInternalBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
        val internalBufferSize = minInternalBufferSize * 4
        Log.d(tag, "minInternalBufferSize = $minInternalBufferSize, " +
                "internalBufferSize = $internalBufferSize, myBufferSize = $myBufferSize")
        val statusPermission =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        if (statusPermission == PackageManager.PERMISSION_GRANTED) {
            audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig,
                audioFormat, internalBufferSize)
            audioRecord?.positionNotificationPeriod = 1000
            audioRecord?.notificationMarkerPosition = 10000
            audioRecord?.setRecordPositionUpdateListener(object : AudioRecord.OnRecordPositionUpdateListener {

                override fun onPeriodicNotification(recorder: AudioRecord?) {
                    Log.d(tag, "onPeriodicNotification")
                }

                override fun onMarkerReached(recorder: AudioRecord?) {
                    Log.d(tag, "onMarkerReached")
                    isReading = false
                }
            })
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_CODE_PERMISSION_RECORD_AUDIO)
            createAudioRecorder()
        }
    }

    fun recordStart() {
        Log.d(tag, "record start")
        audioRecord?.startRecording()
        Log.d(tag, "recordingState = ${audioRecord?.recordingState}")
    }

    fun recordStop() {
        Log.d(tag, "record stop")
        audioRecord?.stop()
    }

    fun readStart() {
        Log.d(tag, "read start")
        isReading = true
        Thread {
            audioRecord?.let {
                val myBuffer = ByteArray(myBufferSize)
                var totalCount = 0
                while (isReading) {
                    val readCount = it.read(myBuffer, 0, myBufferSize)
                    totalCount += readCount
                    Log.d(tag, "readCount = $readCount, totalCount = $totalCount")
                }
            }
        }.start()
    }

    fun readStop() {
        Log.d(tag, "read stop")
        isReading = false
    }

    override fun onDestroy() {
        super.onDestroy()
        isReading = false
        audioRecord?.release()
    }
}