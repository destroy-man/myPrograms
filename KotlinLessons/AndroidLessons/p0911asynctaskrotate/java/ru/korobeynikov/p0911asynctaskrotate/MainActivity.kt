package ru.korobeynikov.p0911asynctaskrotate

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var mt:MyTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("qwe","create MainActivity: ${this.hashCode()}")
        if(lastCustomNonConfigurationInstance==null){
            mt=MyTask()
            mt.execute()
        }
        else
            mt=lastCustomNonConfigurationInstance as MyTask
        mt.link(this)
        Log.d("qwe","create MyTask: ${mt.hashCode()}")
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        mt.unLink()
        return mt
    }

    companion object{
        class MyTask:AsyncTask<String,Int,Unit>(){

            var activity: MainActivity? = null

            fun link(act:MainActivity){
                activity=act
            }

            fun unLink(){
                activity=null
            }

            protected override fun doInBackground(vararg p0: String?):Unit? {
                try{
                    for(i in 1..10){
                        TimeUnit.SECONDS.sleep(1)
                        publishProgress(i)
                        Log.d("qwe","i = $i, MyTask: ${this.hashCode()}, MainActivity: ${activity.hashCode()}")
                    }
                }
                catch(e:InterruptedException){
                    e.printStackTrace()
                }
                return null
            }

            protected override fun onProgressUpdate(vararg values: Int?) {
                super.onProgressUpdate(*values)
                if(activity!=null)
                    activity!!.tv.text="i = ${values[0]}"
            }
        }
    }
}