package ru.korobeynikov.p1271soundpool

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1271soundpool.databinding.ActivityMainBinding
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val logTag = "myLogs"
    private val maxStreams = 5
    private var soundIdShot = 0
    private var soundIdExplosion = 0
    private var streamIDShot = 0
    private var streamIDExplosion = 0
    private lateinit var sp: SoundPool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        val attributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        sp = SoundPool.Builder().setMaxStreams(maxStreams).setAudioAttributes(attributes).build()
        sp.setOnLoadCompleteListener { soundPool, sampleId, status ->
            Log.d(logTag, "onLoadComplete, sampleId = $sampleId, status = $status")
        }
        soundIdShot = sp.load(this, R.raw.shot, 1)
        Log.d(logTag, "soundIdShot = $soundIdShot")
        try {
            soundIdExplosion = sp.load(assets.openFd("explosion.ogg"), 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.d(logTag, "soundIdExplosion = $soundIdExplosion")
    }

    fun clickButton() {
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
}