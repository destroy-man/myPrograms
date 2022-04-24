package ru.korobeynikov.p0821handleradvmessage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    val STATUS_NONE=0
    val STATUS_CONNECTING=1
    val STATUS_CONNECTED=2
    val STATUS_DOWNLOAD_START=3
    val STATUS_DOWNLOAD_FILE=4
    val STATUS_DOWNLOAD_END=5
    val STATUS_DOWNLOAD_NONE=6
    lateinit var h:Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        h=Handler{msg->
            when(msg.what){
                STATUS_NONE->{
                    btnConnect.isEnabled=true
                    tvStatus.text="Not connected"
                    pbDownload.visibility=View.GONE
                }
                STATUS_CONNECTING->{
                    btnConnect.isEnabled=false
                    tvStatus.text="Connecting"
                }
                STATUS_CONNECTED->tvStatus.text="Connected"
                STATUS_DOWNLOAD_START->{
                    tvStatus.text="Start download ${msg.arg1} files"
                    pbDownload.max=msg.arg1
                    pbDownload.progress=0
                    pbDownload.visibility=View.VISIBLE
                }
                STATUS_DOWNLOAD_FILE->{
                    tvStatus.text="Downloading. Left ${msg.arg2} files"
                    pbDownload.progress=msg.arg1
                    saveFile(msg.obj as ByteArray)
                }
                STATUS_DOWNLOAD_END->tvStatus.text="Download complete!"
                STATUS_DOWNLOAD_NONE->tvStatus.text="No files for download"
            }
            false
        }
        h.sendEmptyMessage(STATUS_NONE)
    }

    fun onclick(v:View){
        val t=Thread{
            var msg:Message
            var file:ByteArray
            val rand=Random()
            try{
                h.sendEmptyMessage(STATUS_CONNECTING)
                TimeUnit.SECONDS.sleep(1)
                h.sendEmptyMessage(STATUS_CONNECTED)
                TimeUnit.SECONDS.sleep(1)
                val filesCount=rand.nextInt(5)
                if(filesCount==0){
                    h.sendEmptyMessage(STATUS_DOWNLOAD_NONE)
                    TimeUnit.MILLISECONDS.sleep(1500)
                    h.sendEmptyMessage(STATUS_NONE)
                    return@Thread
                }
                msg=h.obtainMessage(STATUS_DOWNLOAD_START,filesCount,0)
                h.sendMessage(msg)
                for(i in 1..filesCount){
                    file=downloadFile()
                    msg=h.obtainMessage(STATUS_DOWNLOAD_FILE,i,filesCount-i,file)
                    h.sendMessage(msg)
                }
                h.sendEmptyMessage(STATUS_DOWNLOAD_END)
                TimeUnit.MILLISECONDS.sleep(1500)
                h.sendEmptyMessage(STATUS_NONE)
            }
            catch(e:InterruptedException){
                e.printStackTrace()
            }
        }
        t.start()
    }

    @Throws(InterruptedException::class)
    fun downloadFile():ByteArray{
        TimeUnit.SECONDS.sleep(2)
        return ByteArray(1024)
    }

    fun saveFile(file:ByteArray){}
}