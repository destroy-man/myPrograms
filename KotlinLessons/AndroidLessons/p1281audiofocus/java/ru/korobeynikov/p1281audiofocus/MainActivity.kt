package ru.korobeynikov.p1281audiofocus

import android.Manifest
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1281audiofocus.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "myLogs"
        const val REQUEST_CODE_PERMISSION_READ_STORAGE = 1
    }

    private var mpMusic: MediaPlayer? = null
    private var mpSound: MediaPlayer? = null
    private var musicRequestFocus: AudioFocusRequest? = null
    private var soundRequestFocus: AudioFocusRequest? = null
    private lateinit var audioManager: AudioManager
    private lateinit var afListenerMusic: AFListener
    private lateinit var afListenerSound: AFListener

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        readFileMusic()
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
            readFileMusic()
        }

    private fun readFileMusic() {
        mpMusic = MediaPlayer()
        mpMusic?.let { mp ->
            try {
                mp.setDataSource("${Environment.getExternalStorageDirectory().absolutePath}/Music/music.mp3")
                mp.prepare()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            mp.setOnCompletionListener {
                musicRequestFocus?.let { requestFocus ->
                    Log.d(LOG_TAG, "Music: abandon focus")
                    audioManager.abandonAudioFocusRequest(requestFocus)
                }
            }
            afListenerMusic = AFListener(mp, "Music")
            musicRequestFocus = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).run {
                setAudioAttributes(AudioAttributes.Builder().run {
                    setUsage(AudioAttributes.USAGE_MEDIA)
                    setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    build()
                })
                setAcceptsDelayedFocusGain(true)
                setOnAudioFocusChangeListener(afListenerMusic)
                build()
            }
            musicRequestFocus?.let { requestFocus ->
                val requestResult = audioManager.requestAudioFocus(requestFocus)
                Log.d(LOG_TAG, "Music request focus, result: $requestResult")
            }
            mp.start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    fun clickMusic() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            processPermission()
        else
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_READ_STORAGE)
    }

    fun clickSound(view: View) {
        val durationHint = when (view.id) {
            R.id.btnPlaySoundG -> AudioManager.AUDIOFOCUS_GAIN
            R.id.btnPlaySoundGT -> AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
            R.id.btnPlaySoundGTD -> AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
            else -> AudioManager.AUDIOFOCUS_GAIN
        }
        mpSound = MediaPlayer.create(this, R.raw.explosion)
        mpSound?.let { mp ->
            mp.setOnCompletionListener {
                soundRequestFocus?.let { requestFocus ->
                    Log.d(LOG_TAG, "Sound: abandon focus")
                    audioManager.abandonAudioFocusRequest(requestFocus)
                }
            }
            afListenerSound = AFListener(mp, "Sound")
            soundRequestFocus = AudioFocusRequest.Builder(durationHint).run {
                setAudioAttributes(AudioAttributes.Builder().run {
                    setUsage(AudioAttributes.USAGE_ALARM)
                    setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    build()
                })
                setAcceptsDelayedFocusGain(true)
                setOnAudioFocusChangeListener(afListenerSound)
                build()
            }
            soundRequestFocus?.let { requestFocus ->
                val requestResult = audioManager.requestAudioFocus(requestFocus)
                Log.d(LOG_TAG, "Sound request focus, result: $requestResult")
            }
            mp.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mpMusic?.release()
        mpSound?.release()
        musicRequestFocus?.let {
            audioManager.abandonAudioFocusRequest(it)
        }
        soundRequestFocus?.let {
            audioManager.abandonAudioFocusRequest(it)
        }
    }

    class AFListener(private val mp: MediaPlayer, private val label: String) :
        AudioManager.OnAudioFocusChangeListener {
        override fun onAudioFocusChange(focusChange: Int) {
            when (focusChange) {
                AudioManager.AUDIOFOCUS_LOSS -> {
                    Log.d(LOG_TAG, "$label onAudioFocusChange: AUDIOFOCUS_LOSS")
                    mp.pause()
                }
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                    Log.d(LOG_TAG, "$label onAudioFocusChange: AUDIOFOCUS_LOSS_TRANSIENT")
                    mp.pause()
                }
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                    Log.d(LOG_TAG, "$label onAudioFocusChange: AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK")
                    mp.setVolume(0.5f, 0.5f)
                }
                AudioManager.AUDIOFOCUS_GAIN -> {
                    Log.d(LOG_TAG, "$label onAudioFocusChange: AUDIOFOCUS_GAIN")
                    if (!mp.isPlaying)
                        mp.start()
                    mp.setVolume(1f, 1f)
                }
            }
        }
    }
}