package ru.korobeynikov.p0951servicebackpendingintent

import android.app.PendingIntent
import android.content.Intent
import android.support.v7.app.AppCompatActivity
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
        val PARAM_PINTENT="pendingIntent"
        val PARAM_RESULT="result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTask1.text="Task1"
        tvTask2.text="Task2"
        tvTask3.text="Task3"
    }

    fun onClickStart(v:View){
        var intent:Intent
        var pi:PendingIntent=createPendingResult(TASK1_CODE,Intent(),0)
        intent=Intent(this,MyService::class.java).putExtra(PARAM_TIME,7).putExtra(PARAM_PINTENT,pi)
        startService(intent)
        pi=createPendingResult(TASK2_CODE,Intent(),0)
        intent=Intent(this,MyService::class.java).putExtra(PARAM_TIME,4).putExtra(PARAM_PINTENT,pi)
        startService(intent)
        pi=createPendingResult(TASK3_CODE, Intent(),0)
        intent=Intent(this,MyService::class.java).putExtra(PARAM_TIME,6).putExtra(PARAM_PINTENT,pi)
        startService(intent)
    }

    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(LOG_TAG,"requestCode = $requestCode, resultCode = $resultCode")
        if(resultCode== STATUS_START)
            when(requestCode){
                TASK1_CODE->tvTask1.text="Task1 start"
                TASK2_CODE->tvTask2.text="Task2 start"
                TASK3_CODE->tvTask3.text="Task3 start"
            }
        if(resultCode==STATUS_FINISH){
            val result=data?.getIntExtra(PARAM_RESULT,0)
            when(requestCode){
                TASK1_CODE->tvTask1.text="Task1 finish, result = $result"
                TASK2_CODE->tvTask2.text="Task2 finish, result = $result"
                TASK3_CODE->tvTask3.text="Task3 finish, result = $result"
            }
        }
    }
}