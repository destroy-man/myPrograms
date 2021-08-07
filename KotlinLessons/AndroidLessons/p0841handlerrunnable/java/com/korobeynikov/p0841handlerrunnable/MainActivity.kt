package com.korobeynikov.p0841handlerrunnable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    var cnt by Delegates.notNull<Int>()
    val LOG_TAG="myLogs"
    val max=100
    lateinit var h:Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        h=Handler()
        pbCount.max=max
        pbCount.setProgress(0)
        chbInfo.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(isChecked){
                tvInfo.visibility=View.VISIBLE
                h.post(showInfo)
            }
            else{
                tvInfo.visibility=View.GONE
                h.removeCallbacks(showInfo)
            }
        }
        val t=Thread(Runnable{
            try{
                for(cnt in 1..max-1){
                    this.cnt=cnt
                    TimeUnit.MILLISECONDS.sleep(100)
                    h.post(updateProgress)
                }
            }
            catch(e:InterruptedException){
                e.printStackTrace()
            }
        })
        t.start()
    }

    val updateProgress=Runnable{
        pbCount.setProgress(cnt)
    }

    val showInfo=object:Runnable{
        override fun run() {
            Log.d(LOG_TAG,"showInfo")
            tvInfo.text="Count = "+cnt
            h.postDelayed(this,1000)
        }
    }
}