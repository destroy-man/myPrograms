package ru.korobeynikov.p0901asynctaskstatus

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var mt:MyTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onclick(v:View){
        when(v.id){
            R.id.btnStart->startTask()
            R.id.btnStatus->showStatus()
        }
    }

    private fun startTask(){
        mt=MyTask()
        mt.execute()
        mt.cancel(false)
    }

    private fun showStatus(){
        if(mt!=null)
            if(mt.isCancelled)
                Toast.makeText(this,"CANCELLED",Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this,mt.status.toString(),Toast.LENGTH_SHORT).show()
    }

    inner class MyTask:AsyncTask<Unit,Unit,Unit>(){

        protected override fun onPreExecute() {
            super.onPreExecute()
            tvInfo.text="Begin"
        }

        protected override fun doInBackground(vararg p0: Unit?):Unit? {
            try{
                for(i in 0..4){
                    if(isCancelled)return null
                    TimeUnit.SECONDS.sleep(1)
                }
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

        protected override fun onCancelled() {
            super.onCancelled()
            tvInfo.text="Cancel"
        }
    }
}