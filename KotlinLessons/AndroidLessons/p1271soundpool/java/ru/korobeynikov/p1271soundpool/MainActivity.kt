package ru.korobeynikov.p1271soundpool

import android.media.AudioManager
import android.media.SoundPool
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), SoundPool.OnLoadCompleteListener {

    private val LOG_TAG = "myLogs"
    private val MAX_STREAMS = 5
    private lateinit var sp: SoundPool
    var soundIdShot = 0
    var soundIdExplosion = 0
    var streamIDShot = 0
    var streamIDExplosion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0)
        sp.setOnLoadCompleteListener(this)
        soundIdShot = sp.load(this, R.raw.shot, 1)
        Log.d(LOG_TAG, "soundIdShot = $soundIdShot")
        try {
            soundIdExplosion = sp.load(this, R.raw.explosion, 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.d(LOG_TAG, "soundIdExplosion = $soundIdExplosion")
    }

    fun onClick(view: View) {
        streamIDShot = sp.play(soundIdShot, 1f, 0f, 0, 9, 1f)
        streamIDExplosion = sp.play(soundIdExplosion, 0f, 1f, 0, 4, 1f)
        try {
            TimeUnit.SECONDS.sleep(2)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        sp.setVolume(streamIDShot, 0f, 1f)
        sp.setVolume(streamIDExplosion, 1f, 0f)
    }

    override fun onLoadComplete(p0: SoundPool?, sampleId: Int, status: Int) {
        Log.d(LOG_TAG, "onLoadComplete, sampleId = $sampleId, status = $status")
    }
}