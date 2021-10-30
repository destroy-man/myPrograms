package com.korobeynikov.p1162mngtasks2

import android.app.ActivityManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    lateinit var list:List<ActivityManager.RunningTaskInfo>
    lateinit var am:ActivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle(resources.getString(R.string.app_name)+" : "+localClassName)
        am=getSystemService(ACTIVITY_SERVICE) as ActivityManager
    }

    fun onClick(v:View){
        startActivity(Intent("mngtasks1_activity_c"))
    }

    fun onInfoClick(v:View){
        list=am.getRunningTasks(10)
        for(task in list){
            if(task.baseActivity?.flattenToShortString()?.startsWith("com.korobeynikov.p116") == true){
                Log.d(LOG_TAG,"------------------")
                Log.d(LOG_TAG,"Count: "+task.numActivities)
                Log.d(LOG_TAG,"Root: "+task.baseActivity?.flattenToShortString())
                Log.d(LOG_TAG,"Top: "+task.topActivity?.flattenToShortString())
            }
        }
    }
}