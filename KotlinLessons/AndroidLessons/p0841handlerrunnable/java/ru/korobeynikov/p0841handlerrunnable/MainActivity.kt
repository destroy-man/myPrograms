package ru.korobeynikov.p0841handlerrunnable

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var cnt=0
    val LOG_TAG="myLogs"
    val max=100
    lateinit var h:Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        h= Handler()
        pbCount.max=max
        pbCount.progress=0
        chbInfo.setOnCheckedChangeListener{buttonView,isChecked->
            if(isChecked){
                tvInfo.visibility=View.VISIBLE
                h.post(showInfo)
            }
            else{
                tvInfo.visibility=View.GONE
                h.removeCallbacks(showInfo)
            }
        }
        val t=Thread{
            try{
                for(i in 1 until max){
                    cnt=i
                    TimeUnit.MILLISECONDS.sleep(100)
                    h.post(updateProgress)
                }
            }
            catch(e:InterruptedException){
                e.printStackTrace()
            }
        }
        t.start()
    }

    val updateProgress=Runnable{
        pbCount.progress=cnt
    }

    val showInfo=object:Runnable{
        override fun run() {
            Log.d(LOG_TAG,"showInfo")
            tvInfo.text="Count = $cnt"
            h.postDelayed(this,1000)
        }
    }
}