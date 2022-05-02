package ru.korobeynikov.p0851runnableuithread

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val t=Thread{
            try{
                TimeUnit.SECONDS.sleep(2)
                runOnUiThread(runn1)
                TimeUnit.SECONDS.sleep(1)
                tvInfo.postDelayed(runn3,2000)
                tvInfo.post(runn2)
            }
            catch(e:InterruptedException){
                e.printStackTrace()
            }
        }
        t.start()
    }

    val runn1=Runnable{
        tvInfo.text="runn1"
    }

    val runn2=Runnable{
        tvInfo.text="runn2"
    }

    val runn3=Runnable{
        tvInfo.text="runn3"
    }
}