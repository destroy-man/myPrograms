package ru.korobeynikov.p0881asynctaskresult

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

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
            R.id.btnGet->showResult()
        }
    }

    private fun showResult(){
        if(mt==null)return
        var result=-1
        try{
            Log.d(LOG_TAG,"Try to get result")
            result=mt.get(1,TimeUnit.SECONDS)
            Log.d(LOG_TAG,"get returns $result")
            Toast.makeText(this,"get returns $result",Toast.LENGTH_LONG).show()
        }
        catch(e:Exception){
            when(e){
                is InterruptedException,is ExecutionException->e.printStackTrace()
                is TimeoutException->{
                    Log.d(LOG_TAG,"get timeout, result = $result")
                    e.printStackTrace()
                }
            }
        }
    }

    inner class MyTask:AsyncTask<Unit,Unit,Int>(){

        protected override fun onPreExecute() {
            super.onPreExecute()
            tvInfo.text="Begin"
            Log.d(LOG_TAG,"Begin")
        }

        protected override fun doInBackground(vararg p0: Unit?): Int {
            try{
                TimeUnit.SECONDS.sleep(5)
            }
            catch(e:InterruptedException){
                e.printStackTrace()
            }
            return 100500
        }

        protected override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)
            tvInfo.text="End. Result = $result"
            Log.d(LOG_TAG,"End. Result = $result")
        }
    }
}