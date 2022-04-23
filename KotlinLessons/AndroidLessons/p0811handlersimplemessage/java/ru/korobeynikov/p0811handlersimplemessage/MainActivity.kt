package ru.korobeynikov.p0811handlersimplemessage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    val STATUS_NONE=0
    val STATUS_CONNECTING=1
    val STATUS_CONNECTED=2
    lateinit var h:Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        h=Handler{msg->
            when(msg.what){
                STATUS_NONE->{
                    btnConnect.isEnabled=true
                    tvStatus.text="Not connected"
                }
                STATUS_CONNECTING->{
                    btnConnect.isEnabled=false
                    pbConnect.visibility=View.VISIBLE
                    tvStatus.text="Connecting"
                }
                STATUS_CONNECTED->{
                    pbConnect.visibility=View.GONE
                    tvStatus.text="Connected"
                }
            }
            false
        }
        h.sendEmptyMessage(STATUS_NONE)
    }

    fun onclick(v:View){
        val t=Thread{
            try{
                h.sendEmptyMessage(STATUS_CONNECTING)
                TimeUnit.SECONDS.sleep(2)
                h.sendEmptyMessage(STATUS_CONNECTED)
                TimeUnit.SECONDS.sleep(3)
                h.sendEmptyMessage(STATUS_NONE)
            }
            catch(e:InterruptedException){
                e.printStackTrace()
            }
        }
        t.start()
    }
}