package com.korobeynikov.p0861asynctask

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var mt:MyTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onclick(v:View){
        mt=MyTask()
        mt.execute()
    }

    inner class MyTask:AsyncTask<Unit,Unit,Unit>(){

        protected override fun onPreExecute() {
            super.onPreExecute()
            tvInfo.text="Begin"
        }

        protected override fun doInBackground(vararg params: Unit?): Unit? {
            try{
                TimeUnit.SECONDS.sleep(2)
            }
            catch(e:InterruptedException){
                e.printStackTrace()
            }
            return null
        }

        protected override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            tvInfo.text="End"
        }
    }
}