package ru.korobeynikov.p0871asynctaskparams

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
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
        mt.execute("file_path_1","file_path_2","file_path_3","file_path_4")
    }

    inner class MyTask:AsyncTask<String,Int,Unit>(){

        protected override fun onPreExecute() {
            super.onPreExecute()
            tvInfo.text="Begin"
        }

        protected override fun doInBackground(vararg urls: String?):Unit? {
            try{
                for((cnt, url) in urls.withIndex()){
                    downloadFile(url)
                    publishProgress(cnt + 1)
                }
                TimeUnit.SECONDS.sleep(1)
            }
            catch(e:InterruptedException){
                e.printStackTrace()
            }
            return null
        }

        protected override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            tvInfo.text="Downloaded ${values[0]} files"
        }

        protected override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            tvInfo.text="End"
        }

        @Throws(InterruptedException::class)
        private fun downloadFile(url:String?){
            TimeUnit.SECONDS.sleep(2)
        }
    }
}