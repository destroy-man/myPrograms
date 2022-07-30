package ru.korobeynikov.p1261mediaplayer

import android.content.ContentUris
import android.media.AudioManager
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener {

    val LOG_TAG="myLogs"
    val DATA_HTTP="http://dl.dropboxusercontent.com/u/6197740/explosion.mp3"
    val DATA_STREAM="http://online.radiorecord.ru:8101/rr_128"
    val DATA_SD="${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)}/music.mp3"
    val DATA_URI=ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,13359)
    var mediaPlayer:MediaPlayer?=null
    lateinit var am:AudioManager

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        am=getSystemService(AUDIO_SERVICE) as AudioManager
        chbLoop.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(mediaPlayer!=null)
                mediaPlayer?.isLooping=isChecked
        }
    }

    fun onClickStart(view:View){
        releaseMP()
        try{
            when(view.id){
                R.id.btnStartHttp->{
                    Log.d(LOG_TAG,"start HTTP")
                    mediaPlayer=MediaPlayer()
                    mediaPlayer?.setDataSource(DATA_HTTP)
                    mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    Log.d(LOG_TAG,"prepareAsync")
                    mediaPlayer?.setOnPreparedListener(this)
                    mediaPlayer?.prepareAsync()
                }
                R.id.btnStartStream->{
                    Log.d(LOG_TAG,"start Stream")
                    mediaPlayer=MediaPlayer()
                    mediaPlayer?.setDataSource(DATA_STREAM)
                    mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    Log.d(LOG_TAG,"prepareAsync")
                    mediaPlayer?.setOnPreparedListener(this)
                    mediaPlayer?.prepareAsync()
                }
                R.id.btnStartSD->{
                    Log.d(LOG_TAG,"start SD")
                    mediaPlayer=MediaPlayer()
                    mediaPlayer?.setDataSource(DATA_SD)
                    mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer?.prepare()
                    mediaPlayer?.start()
                }
                R.id.btnStartUri->{
                    Log.d(LOG_TAG,"start Uri")
                    mediaPlayer=MediaPlayer()
                    mediaPlayer?.setDataSource(this,DATA_URI)
                    mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer?.prepare()
                    mediaPlayer?.start()
                }
                R.id.btnStartRaw->{
                    Log.d(LOG_TAG,"start Raw")
                    mediaPlayer=MediaPlayer.create(this,R.raw.explosion)
                    mediaPlayer?.start()
                }
            }
        }
        catch(e:Exception){
            e.printStackTrace()
        }
        if(mediaPlayer==null)return
        mediaPlayer?.isLooping=chbLoop.isChecked
        mediaPlayer?.setOnCompletionListener(this)
    }

    private fun releaseMP(){
        if(mediaPlayer!=null){
            try{
                mediaPlayer?.release()
                mediaPlayer=null
            }
            catch(e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun onClick(view:View){
        if(mediaPlayer==null)return
        when(view.id){
            R.id.btnPause->
                if(mediaPlayer!!.isPlaying)
                    mediaPlayer?.pause()
            R.id.btnResume->
                if(!mediaPlayer!!.isPlaying)
                    mediaPlayer?.start()
            R.id.btnStop->mediaPlayer?.stop()
            R.id.btnBackward->mediaPlayer?.seekTo(mediaPlayer!!.currentPosition-3000)
            R.id.btnForward->mediaPlayer?.seekTo(mediaPlayer!!.currentPosition+3000)
            R.id.btnInfo->{
                Log.d(LOG_TAG,"Playing ${mediaPlayer?.isPlaying}")
                Log.d(LOG_TAG,"Time ${mediaPlayer?.currentPosition}/${mediaPlayer?.duration}")
                Log.d(LOG_TAG,"Looping ${mediaPlayer?.isLooping}")
                Log.d(LOG_TAG,"Volume ${am.getStreamVolume(AudioManager.STREAM_MUSIC)}")
            }
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        Log.d(LOG_TAG,"onPrepared")
        mp?.start()
    }

    override fun onCompletion(p0: MediaPlayer?) {
        Log.d(LOG_TAG,"onCompletion")
    }

    protected override fun onDestroy() {
        super.onDestroy()
        releaseMP()
    }
}