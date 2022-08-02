package ru.korobeynikov.p1301audiorecorder

import android.Manifest
import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {

    val TAG="myLogs"
    private val myBufferSize=8192
    lateinit var audioRecord: AudioRecord
    var isReading=false

    private val REQUEST_CODE_PERMISSION_RECORD_AUDIO=1

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_CODE_PERMISSION_RECORD_AUDIO->recordAudio()
        }
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createAudioRecorder()
    }

    private fun createAudioRecorder(){
        ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.RECORD_AUDIO),REQUEST_CODE_PERMISSION_RECORD_AUDIO)
    }

    @SuppressLint("MissingPermission")
    fun recordAudio(){
        val sampleRate=8000
        val channelConfig=AudioFormat.CHANNEL_IN_MONO
        val audioFormat=AudioFormat.ENCODING_PCM_16BIT
        val minInternalBufferSize=AudioRecord.getMinBufferSize(sampleRate,channelConfig,audioFormat)
        val internalBufferSize=minInternalBufferSize*4
        Log.d(TAG,"minInternalBufferSize = $minInternalBufferSize, internalBufferSize = $internalBufferSize, " +
                "myBufferSize = $myBufferSize")
        audioRecord=AudioRecord(MediaRecorder.AudioSource.MIC,sampleRate,channelConfig,audioFormat,internalBufferSize)
        Log.d(TAG,"init state = ${audioRecord.state}")

        audioRecord.positionNotificationPeriod = 1000
        audioRecord.notificationMarkerPosition = 10000
        audioRecord.setRecordPositionUpdateListener(object:AudioRecord.OnRecordPositionUpdateListener{
            override fun onMarkerReached(p0: AudioRecord?) {
                Log.d(TAG,"onPeriodicNotification")
            }

            override fun onPeriodicNotification(p0: AudioRecord?) {
                Log.d(TAG,"onMarkerReached")
                isReading=false
            }

        })
    }

    fun recordStart(v:View){
        Log.d(TAG,"record start")
        audioRecord.startRecording()
        val recordingState=audioRecord.recordingState
        Log.d(TAG,"recordingState = $recordingState")
    }

    fun recordStop(v: View){
        Log.d(TAG,"record stop")
        audioRecord.stop()
    }

    fun readStart(v:View){
        Log.d(TAG,"read start")
        isReading=true
        Thread{
            if(audioRecord==null)
                return@Thread
            val myBuffer:Array<Byte> = Array(myBufferSize){0}
            var readCount=0
            var totalCount=0
            while (isReading){
                readCount=audioRecord.read(myBuffer.toByteArray(),0,myBufferSize)
                totalCount+=readCount
                Log.d(TAG,"readCount = $readCount, totalCount = $totalCount")
            }
        }.start()
    }

    fun readStop(v:View){
        Log.d(TAG,"read stop")
        isReading=false
    }

    override fun onDestroy() {
        super.onDestroy()
        isReading=false
        if(audioRecord!=null)
            audioRecord.release()
    }
}