package com.korobeynikov.p0961servicebackbroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    val TASK1_CODE=1
    val TASK2_CODE=2
    val TASK3_CODE=3

    companion object{
        val STATUS_START=100
        val STATUS_FINISH=200
        val PARAM_TIME="time"
        val PARAM_TASK="task"
        val PARAM_RESULT="result"
        val PARAM_STATUS="status"
        val BROADCAST_ACTION="com.korobeynikov.p0961servicebackbroadcast"
    }

    lateinit var br:BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTask1.text="Task1"
        tvTask2.text="Task2"
        tvTask3.text="Task3"
        br=object:BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                val task=intent?.getIntExtra(PARAM_TASK,0)
                val status=intent?.getIntExtra(PARAM_STATUS,0)
                Log.d(LOG_TAG,"onReceive: task = "+task+", status = "+status)
                if(status==STATUS_START)
                    when(task){
                        TASK1_CODE->tvTask1.text="Task1 start"
                        TASK2_CODE->tvTask2.text="Task2 start"
                        TASK3_CODE->tvTask3.text="Task3 start"
                    }
                if(status== STATUS_FINISH){
                    val result=intent.getIntExtra(PARAM_RESULT,0)
                    when(task){
                        TASK1_CODE->tvTask1.text="Task1 finish, result = "+result
                        TASK2_CODE->tvTask2.text="Task2 finish, result = "+result
                        TASK3_CODE->tvTask3.text="Task3 finish, result = "+result
                    }
                }
            }

        }
        val intFilt=IntentFilter(BROADCAST_ACTION)
        registerReceiver(br,intFilt)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(br)
    }

    fun onClickStart(v:View){
        var intent:Intent
        intent=Intent(this,MyService::class.java).putExtra(PARAM_TIME,7).putExtra(PARAM_TASK,TASK1_CODE)
        startService(intent)
        intent=Intent(this,MyService::class.java).putExtra(PARAM_TIME,4).putExtra(PARAM_TASK,TASK2_CODE)
        startService(intent)
        intent=Intent(this,MyService::class.java).putExtra(PARAM_TIME,6).putExtra(PARAM_TASK,TASK3_CODE)
        startService(intent)
    }
}