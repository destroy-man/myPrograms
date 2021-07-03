package com.korobeynikov.p0661alertdialogoperations

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    val DIALOG=1
    lateinit var dialog:Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    protected override fun onCreateDialog(id:Int):Dialog{
        if(id==DIALOG){
            Log.d(LOG_TAG,"Create")
            val adb=AlertDialog.Builder(this)
            adb.setTitle("Title")
            adb.setMessage("Message")
            adb.setPositiveButton("OK",null)
            dialog=adb.create()
            dialog.setOnShowListener{
                Log.d(LOG_TAG,"Show")
            }
            dialog.setOnCancelListener{
                Log.d(LOG_TAG,"Cancel")
            }
            dialog.setOnDismissListener{
                Log.d(LOG_TAG,"Dismiss")
            }
            return dialog
        }
        return super.onCreateDialog(id)
    }

    fun method1(){
        removeDialog(DIALOG)
    }

    fun method2(){
        showDialog(DIALOG)
    }

    fun onclick(v:View){
        showDialog(DIALOG)
        val h=Handler()
        h.postDelayed(Runnable{
            method1()
        },2000)
        h.postDelayed(Runnable{
            method2()
        },4000)
    }
}