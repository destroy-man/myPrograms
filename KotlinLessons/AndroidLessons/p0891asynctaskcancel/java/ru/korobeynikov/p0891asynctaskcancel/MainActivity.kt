package ru.korobeynikov.p0891asynctaskcancel

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    lateinit var mt:MyTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onclick(v:View){
        when(v.id){
            R.id.btnStart->{
                mt=MyTask()
                mt.execute()
            }
            R.id.btnCancel->cancelTask()
        }
    }

    private fun cancelTask(){
        if(mt==null)return
        Log.d(LOG_TAG,"cancel result: ${mt.cancel(true)}")
    }

    inner class MyTask:AsyncTask<Unit,Unit,Unit>(){

        protected override fun onPreExecute() {
            super.onPreExecute()
            tvInfo.text="Begin"
            Log.d(LOG_TAG,"Begin")
        }

        protected override fun doInBackground(vararg p0: Unit?):Unit? {
            try{
                for(i in 0..4){
                    TimeUnit.SECONDS.sleep(1)
                    //if(isCancelled)return null
                    Log.d(LOG_TAG,"isCancelled: $isCancelled")
                }
            }
            catch(e:InterruptedException){
                Log.d(LOG_TAG,"Interrupted")
                e.printStackTrace()
            }
            return null
        }

        protected override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            tvInfo.text="End"
            Log.d(LOG_TAG,"End")
        }

        protected override fun onCancelled() {
            super.onCancelled()
            tvInfo.text="Cancel"
            Log.d(LOG_TAG,"Cancel")
        }
    }
}