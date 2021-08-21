package com.korobeynikov.p0911asynctaskrotate

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var mt:MyTask?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("qwe","create MainActivity: "+this.hashCode())
        mt=lastCustomNonConfigurationInstance as? MyTask
        if(mt==null){
            mt=MyTask()
            mt!!.execute()
        }
        mt!!.link(this)
        Log.d("qwe", "create MyTask: " + mt.hashCode())
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        mt?.unlink()
        return mt
    }

    companion object class MyTask:AsyncTask<String,Int,Unit>(){

        var activity:MainActivity?=null

        fun link(act:MainActivity){
            activity=act
        }

        fun unlink(){
            activity=null
        }

        protected override fun doInBackground(vararg params: String?): Unit? {
            try{
                for(i in 1..10){
                    TimeUnit.SECONDS.sleep(1)
                    publishProgress(i)
                    Log.d("qwe","i = "+i+", MyTask: "+this.hashCode()+", MainActivity: "+ activity.hashCode())
                }
            }
            catch(e:InterruptedException){
                e.printStackTrace()
            }
            return null
        }

        protected override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            activity!!.tv.text="i = "+values[0]
        }
    }
}