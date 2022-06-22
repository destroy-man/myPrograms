package ru.korobeynikov.p1161mngtasks1

import android.app.ActivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

abstract class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    lateinit var list:List<ActivityManager.RunningTaskInfo>
    lateinit var am:ActivityManager

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title="${resources.getString(R.string.app_name)} : $localClassName"
        am=getSystemService(ACTIVITY_SERVICE) as ActivityManager
    }

    fun onInfoClick(v:View){
        list=am.getRunningTasks(10)
        for(task in list){
            if(task.baseActivity?.flattenToShortString()?.startsWith("ru.korobeynikov.p116")==true){
                Log.d(LOG_TAG,"------------------")
                Log.d(LOG_TAG,"Count: ${task.numActivities}")
                Log.d(LOG_TAG,"Root: ${task.baseActivity?.flattenToShortString()}")
                Log.d(LOG_TAG,"Top: ${task.topActivity?.flattenToShortString()}")
            }
        }
    }

    abstract fun onClick(v:View)
}