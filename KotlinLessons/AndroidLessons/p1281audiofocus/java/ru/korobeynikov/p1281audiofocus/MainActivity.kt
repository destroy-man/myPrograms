package ru.korobeynikov.p1281audiofocus

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity(), MediaPlayer.OnCompletionListener {

    companion object {
        const val LOG_TAG = "myLogs"
    }

    private lateinit var audioManager: AudioManager
    lateinit var afListenerMusic: AFListener
    lateinit var afListenerSound: AFListener
    private lateinit var mpMusic: MediaPlayer
    private lateinit var mpSound: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    fun onClickMusic(view: View) {
        mpMusic = MediaPlayer.create(this, R.raw.music)
        mpMusic.setOnCompletionListener(this)
        afListenerMusic = AFListener(mpMusic, "Music")
        val requestResult = audioManager.requestAudioFocus(afListenerMusic, AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN)
        Log.d(LOG_TAG, "Music request focus, result: $requestResult")
        mpMusic.start()
    }

    fun onClickSound(view: View) {
        var durationHint = AudioManager.AUDIOFOCUS_GAIN
        when (view.id) {
            R.id.btnPlaySoundG -> durationHint = AudioManager.AUDIOFOCUS_GAIN
            R.id.btnPlaySoundGT -> durationHint = AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
            R.id.btnPlaySoundGTD -> durationHint = AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
        }
        mpSound = MediaPlayer.create(this, R.raw.explosion)
        mpSound.setOnCompletionListener(this)
        afListenerSound = AFListener(mpSound, "Sound")
        val requestResult = audioManager.requestAudioFocus(afListenerSound, AudioManager.STREAM_MUSIC,
            durationHint)
        Log.d(LOG_TAG, "Sound request focus, result: $requestResult")
        mpSound.start()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (mp == mpMusic) {
            Log.d(LOG_TAG, "Music: abandon focus")
            audioManager.abandonAudioFocus(afListenerMusic)
        } else if (mp == mpSound) {
            Log.d(LOG_TAG, "Sound: abandon focus")
            audioManager.abandonAudioFocus(afListenerSound)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mpMusic != null)
            mpMusic.release()
        if (mpSound != null)
            mpSound.release()
        if (afListenerMusic != null)
            audioManager.abandonAudioFocus(afListenerMusic)
        if (afListenerSound != null)
            audioManager.abandonAudioFocus(afListenerSound)
    }

    class AFListener(private val mp: MediaPlayer, private val label: String) :
        AudioManager.OnAudioFocusChangeListener {
        override fun onAudioFocusChange(focusChange: Int) {
            var event = ""
            when (focusChange) {
                AudioManager.AUDIOFOCUS_LOSS -> {
                    event = "AUDIOFOCUS_LOSS"
                    mp.pause()
                }
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                    event = "AUDIOFOCUS_LOSS_TRANSIENT"
                    mp.pause()
                }
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                    event = "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK"
                    mp.setVolume(0.5f, 0.5f)
                }
                AudioManager.AUDIOFOCUS_GAIN -> {
                    event = "AUDIOFOCUS_GAIN"
                    if (!mp.isPlaying) mp.start()
                    mp.setVolume(1f, 1f)
                }
            }
            Log.d(LOG_TAG, "$label onAudioFocusChange: $event")
        }
    }
}